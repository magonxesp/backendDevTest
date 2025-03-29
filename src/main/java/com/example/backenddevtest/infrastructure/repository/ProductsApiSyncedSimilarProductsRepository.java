package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.domain.SimilarProductsRepository;
import com.example.backenddevtest.infrastructure.client.ProductsApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsApiSyncedSimilarProductsRepository implements SimilarProductsRepository {
    private final MongoDBSimilarProductsJPARepository similarRepository;
    private final MongoDBProductDetailRepository productDetailRepository;
    private final ProductsApiClient client;

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
    public List<ProductDetail> findSimilar(String productId) {
        // TODO: get similars from client and sync details with mongodb
        // client.getProductSimilarIds(productId);
        return List.of();
    }

    // TODO: private method for get similars from products api client

    // TODO: private method for get similars from mongo
}
