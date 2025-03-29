package com.example.backenddevtest.infrastructure.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("similar_products")
public class MongoDBSimilarProductsDocument {
    @Id
    private String productId;
    private List<String> similarIds;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<String> getSimilarIds() {
        return similarIds;
    }

    public void setSimilarIds(List<String> similarIds) {
        this.similarIds = similarIds;
    }
}
