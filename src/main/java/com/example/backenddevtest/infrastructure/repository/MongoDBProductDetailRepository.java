package com.example.backenddevtest.infrastructure.repository;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.mapper.ProductDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MongoDBProductDetailRepository {
    private final ProductDetailMapper mapper;
    private final MongoDBProductDetailJPARepository jpaRepository;

    public MongoDBProductDetailRepository(ProductDetailMapper mapper, MongoDBProductDetailJPARepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    public List<ProductDetail> findAllById(List<String> ids) {
        return mapper.mongoDocumentsToDomainEntityList(jpaRepository.findAllById(ids));
    }

    @Transactional
    public void save(ProductDetail... details) {
        jpaRepository.saveAll(mapper.domainEntitiesToMongoDocuments(details));
    }
}
