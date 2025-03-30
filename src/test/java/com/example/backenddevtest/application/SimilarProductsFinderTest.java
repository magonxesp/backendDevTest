package com.example.backenddevtest.application;

import com.example.backenddevtest.UnitTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.domain.SimilarProductsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimilarProductsFinderTest extends UnitTestCase {
    @Mock
    private SimilarProductsRepository similarProductsRepository;

    @InjectMocks
    private SimilarProductsFinder similarProductsFinder;

    @Test
    public void similarProducts_shouldReturnSimilarProducts() {
        String productId = "1";
        List<ProductDetail> similar = List.of(
                new ProductDetail("2", "Dress", 19.99, true),
                new ProductDetail("3", "Blazer", 29.99, false),
                new ProductDetail("4", "Boots", 39.99, true)
        );

        when(similarProductsRepository.findSimilar(productId)).thenReturn(similar);

        List<ProductDetail> actual = similarProductsFinder.similarProducts(productId);

        assertEquals(similar, actual);

        verify(similarProductsRepository).findSimilar(productId);
    }
}
