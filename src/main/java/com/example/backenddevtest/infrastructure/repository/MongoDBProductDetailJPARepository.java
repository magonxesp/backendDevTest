package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.infrastructure.model.MongoDBProductDetailDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBProductDetailJPARepository extends CrudRepository<MongoDBProductDetailDocument, String> {
}
