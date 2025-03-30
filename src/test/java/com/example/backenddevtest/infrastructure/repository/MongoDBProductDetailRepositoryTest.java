package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.IntegrationTestCase;
import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.model.MongoDBProductDetailDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoDBProductDetailRepositoryTest extends IntegrationTestCase {
    @Autowired
    private MongoDBProductDetailRepository repository;

    @Test
    public void saveAll_shouldSaveAllProductDetail() {
        ProductDetail detail1 = new ProductDetail(UUID.randomUUID().toString(), "Shirt", 9.99, true);
        ProductDetail detail2 = new ProductDetail(UUID.randomUUID().toString(), "Shirt2", 9.99, true);
        List<ProductDetail> details = List.of(detail1, detail2);

        repository.save(detail1, detail2);

        List<ProductDetail> saved = repository.findAllById(List.of(detail1.id(), detail2.id()));

        assertEquals(details, saved);
    }
}
