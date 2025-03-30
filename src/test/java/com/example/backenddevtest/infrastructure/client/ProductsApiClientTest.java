package com.example.backenddevtest.infrastructure.client;

import com.example.backenddevtest.IntegrationTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.model.ProductsApiProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsApiClientTest extends IntegrationTestCase {
    @Autowired
    private ProductsApiClient productsApiClient;

    @Test
    public void getProductDetail_shouldReturnProductDetailById() throws IOException {
        ProductDetail expected = new ProductDetail("1", "Shirt", 9.99, true);

        Optional<ProductDetail> productDetail = productsApiClient.getProductDetail("1");

        assertTrue(productDetail.isPresent());
        assertEquals(expected, productDetail.get());
    }

    @Test
    public void getProductDetail_shouldReturnEmptyIfStatusCodeIs404() throws IOException {
        Optional<ProductDetail> productDetail = productsApiClient.getProductDetail("5");

        assertTrue(productDetail.isEmpty());
    }

    @Test
    public void getProductDetail_shouldReturnEmptyIfStatusCodeIs500() throws IOException {
        Optional<ProductDetail> productDetail = productsApiClient.getProductDetail("6");

        assertTrue(productDetail.isEmpty());
    }

    @Test
    public void getProductSimilarIds_shouldReturnProductSimilarIds() throws IOException {
        List<String> expected = List.of("2", "3", "4");

        List<String> similarIds = productsApiClient.getProductSimilarIds("1");

        assertThat(similarIds).containsExactlyInAnyOrderElementsOf(expected);
    }
}
