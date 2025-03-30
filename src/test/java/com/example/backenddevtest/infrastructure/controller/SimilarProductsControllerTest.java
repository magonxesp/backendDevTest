package com.example.backenddevtest.infrastructure.controller;

import com.example.backenddevtest.IntegrationTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SimilarProductsControllerTest extends IntegrationTestCase {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void similarProducts_shouldReturnSimilarProducts() {
        List<ProductDetail> expected = List.of(
                new ProductDetail("2", "Dress", 19.99, true),
                new ProductDetail("3", "Blazer", 29.99, false),
                new ProductDetail("4", "Boots", 39.99, true)
        );

        String url = String.format("http://localhost:%s/product/1/similar", port);
        ResponseEntity<ProductDetail[]> response = restTemplate.getForEntity(url, ProductDetail[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<ProductDetail> actual = Arrays.asList(response.getBody());
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
