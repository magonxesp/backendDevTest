package com.example.backenddevtest.infrastructure.controller;

import com.example.backenddevtest.application.SimilarProductsFinder;
import com.example.backenddevtest.domain.ProductDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimilarProductsController {
    private final SimilarProductsFinder similarProductsFinder;

    public SimilarProductsController(SimilarProductsFinder similarProductsFinder) {
        this.similarProductsFinder = similarProductsFinder;
    }

    @GetMapping("/product/{productId}/similar")
    public List<ProductDetail> similarProducts(@PathVariable String productId) {
        return similarProductsFinder.similarProducts(productId);
    }
}
