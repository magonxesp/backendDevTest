package com.example.backenddevtest.domain;

import java.util.List;

public interface SimilarProductsRepository {
    List<ProductDetail> findSimilar(String productId);
}
