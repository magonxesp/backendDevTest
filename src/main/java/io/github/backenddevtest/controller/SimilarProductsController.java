package io.github.backenddevtest.controller;

import io.github.backenddevtest.model.ProductDetail;
import io.github.backenddevtest.service.SimilarProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimilarProductsController {
    private final SimilarProductsService similarProductsService;

    public SimilarProductsController(SimilarProductsService similarProductsService) {
        this.similarProductsService = similarProductsService;
    }

    @GetMapping("/product/{id}/similar")
    public List<ProductDetail> getSimilarProducts(@PathVariable String id) {
        return similarProductsService.similarProducts(id);
    }
}
