package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.IntegrationTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ProductsApiSyncedSimilarProductsRepositoryTest extends IntegrationTestCase {
    @Autowired
    private ProductsApiSyncedSimilarProductsRepository repository;

    @Test
    public void findSimilar_shouldReturnSimilarProducts() {
        List<ProductDetail> similar = List.of(
                new ProductDetail("2", "Dress", 19.99, true),
                new ProductDetail("3", "Blazer", 29.99, false),
                new ProductDetail("4", "Boots", 39.99, true)
        );

        List<ProductDetail> foundSimilar = repository.findSimilar("1");

        assertEquals(similar, foundSimilar);
    }
}
