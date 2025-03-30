package com.example.backenddevtest.infrastructure.controller;

import com.example.backenddevtest.application.SimilarProductsFinder;
import com.example.backenddevtest.domain.ProductDetail;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductDetail>> similarProducts(@PathVariable String productId) {
        List<ProductDetail> similar = similarProductsFinder.similarProducts(productId);
        if (similar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(similar);
    }
}
