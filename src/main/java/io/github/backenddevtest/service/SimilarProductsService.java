package io.github.backenddevtest.service;

import io.github.backenddevtest.model.ProductDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SimilarProductsService {
    private final RestTemplate restTemplate;

    public SimilarProductsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDetail> similarProducts(String productId) {
        return Arrays.stream(fetchSimilarProductsId(productId))
                .map(this::fetchProductDetails)
                .toList();
    }

    private String[] fetchSimilarProductsId(String productId) {
        final String url = "http://localhost:3001/product/%s/similarids".formatted(productId);
        return restTemplate.getForObject(url, String[].class);
    }

    private ProductDetail fetchProductDetails(String productId) {
        final String url = "http://localhost:3001/product/%s".formatted(productId);
        return restTemplate.getForObject(url, ProductDetail.class);
    }
}
