package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.domain.SimilarProductsRepository;
import com.example.backenddevtest.infrastructure.client.ProductsApiClient;
import com.example.backenddevtest.infrastructure.model.MongoDBSimilarProductsDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Service
public class ProductsApiSyncedSimilarProductsRepository implements SimilarProductsRepository {
    private final MongoDBSimilarProductsJPARepository similarRepository;
    private final MongoDBProductDetailRepository productDetailRepository;
    private final ProductsApiClient client;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${products-api.cache.ttl-ms:60000}")
    private long ttl = 60_000;
    private static final AtomicLong synchronizedAt = new AtomicLong(0);

    public ProductsApiSyncedSimilarProductsRepository(
            MongoDBSimilarProductsJPARepository similarRepository,
            MongoDBProductDetailRepository productDetailRepository,
            ProductsApiClient client
    ) {
        this.similarRepository = similarRepository;
        this.productDetailRepository = productDetailRepository;
        this.client = client;
    }

    @Override
    @Transactional
    public List<ProductDetail> findSimilar(String productId) {
        long now = Instant.now().toEpochMilli();
        boolean needSynchronization = synchronizedAt.get() == 0 || now > synchronizedAt.get() + ttl;

        if (needSynchronization) {
            return fetchAndSynchronize(productId);
        }

        List<ProductDetail> similarProducts = List.of();
        Optional<MongoDBSimilarProductsDocument> document = similarRepository.findById(productId);
        if (document.isPresent()) {
            similarProducts = productDetailRepository.findAllById(document.get().getSimilarIds());
        }

        if (similarProducts.isEmpty()) {
            return fetchAndSynchronize(productId);
        }

        return similarProducts;
    }

    private List<ProductDetail> fetchAndSynchronize(String productId) {
        long now = Instant.now().toEpochMilli();
        List<ProductDetail> similarProducts = fetchSimilarProducts(productId);
        synchronizeSimilarProducts(productId, similarProducts);
        synchronizedAt.set(now);

        return similarProducts;
    }

    private List<ProductDetail> fetchSimilarProducts(String productId) {
        Function<String, ProductDetail> fetchProductDetails = id -> {
            try {
                return client.getProductDetail(id).orElse(null);
            } catch (IOException exception) {
                logger.warn("Failed fetching product details for product with id {}", id, exception);
                return null;
            }
        };

        try {
            return client.getProductSimilarIds(productId).stream()
                    .map(fetchProductDetails)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException exception) {
            logger.warn("Failed fetching similar product for product with id {}", productId, exception);
            return List.of();
        }
    }

    private void synchronizeSimilarProducts(String productId, List<ProductDetail> similar) {
        if (similar.isEmpty()) {
            return;
        }

        similarRepository.save(MongoDBSimilarProductsDocument.fromProductDetailList(productId, similar));

        ProductDetail[] toSave = similar.toArray(new ProductDetail[0]);
        productDetailRepository.save(toSave);
    }
}
