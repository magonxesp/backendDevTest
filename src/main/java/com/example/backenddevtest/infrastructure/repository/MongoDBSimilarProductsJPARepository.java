package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.infrastructure.model.MongoDBSimilarProductsDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBSimilarProductsJPARepository extends CrudRepository<MongoDBSimilarProductsDocument, String> {
}
