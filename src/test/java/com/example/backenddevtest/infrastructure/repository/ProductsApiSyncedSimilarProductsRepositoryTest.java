package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.IntegrationTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(foundSimilar).containsExactlyInAnyOrderElementsOf(similar);
    }

    @Test
    public void findSimilar_shouldReturnCachedSimilarProducts() {
        List<ProductDetail> similar = List.of(
                new ProductDetail("2", "Dress", 19.99, true),
                new ProductDetail("3", "Blazer", 29.99, false),
                new ProductDetail("4", "Boots", 39.99, true)
        );

        repository.findSimilar("1"); // first fetch and synchronization
        List<ProductDetail> foundSimilar = repository.findSimilar("1");

        assertThat(foundSimilar).containsExactlyInAnyOrderElementsOf(similar);
    }

    @Test
    public void findSimilar_shouldSynchronizeAfterTtlExpires() throws InterruptedException {
        List<ProductDetail> similar = List.of(
                new ProductDetail("2", "Dress", 19.99, true),
                new ProductDetail("3", "Blazer", 29.99, false),
                new ProductDetail("4", "Boots", 39.99, true)
        );

        repository.findSimilar("1"); // first fetch and synchronization
        Thread.sleep(Duration.of(1, ChronoUnit.MINUTES));
        List<ProductDetail> foundSimilar = repository.findSimilar("1");

        assertThat(foundSimilar).containsExactlyInAnyOrderElementsOf(similar);
    }
}
