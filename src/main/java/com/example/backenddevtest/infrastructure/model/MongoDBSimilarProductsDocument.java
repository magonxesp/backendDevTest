package com.example.backenddevtest.infrastructure.model;

import com.example.backenddevtest.domain.ProductDetail;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("similar_products")
public class MongoDBSimilarProductsDocument {
    @Id
    private String productId;
    private List<String> similarIds;

    public MongoDBSimilarProductsDocument() {
    }

    public MongoDBSimilarProductsDocument(String productId, List<String> similarIds) {
        this.productId = productId;
        this.similarIds = similarIds;
    }

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

    public static MongoDBSimilarProductsDocument fromProductDetailList(String productId, List<ProductDetail> similar) {
        return new MongoDBSimilarProductsDocument(
                productId,
                similar.stream().map(ProductDetail::id).toList()
        );
    }
}
