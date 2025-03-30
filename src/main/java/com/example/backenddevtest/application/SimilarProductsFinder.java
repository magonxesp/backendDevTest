package com.example.backenddevtest.application;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.domain.SimilarProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarProductsFinder {
    private final SimilarProductsRepository similarProductsRepository;

    public SimilarProductsFinder(SimilarProductsRepository similarProductsRepository) {
        this.similarProductsRepository = similarProductsRepository;
    }

    public List<ProductDetail> similarProducts(String productId) {
        return similarProductsRepository.findSimilar(productId);
    }
}
