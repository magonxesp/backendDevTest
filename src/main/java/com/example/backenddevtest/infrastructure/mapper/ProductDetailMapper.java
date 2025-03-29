package com.example.backenddevtest.infrastructure.mapper;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.model.MongoDBProductDetailDocument;
import com.example.backenddevtest.infrastructure.model.ProductsApiProductDetail;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductDetailMapper {
    public abstract ProductDetail toDomainEntity(MongoDBProductDetailDocument document);
    public abstract ProductDetail toDomainEntity(ProductsApiProductDetail detail);
    public abstract MongoDBProductDetailDocument toMongoDocument(ProductDetail detail);

    public List<ProductDetail> mongoDocumentsToDomainEntityList(Iterable<MongoDBProductDetailDocument> documents) {
        List<ProductDetail> details = new ArrayList<>();
        documents.forEach(document -> details.add(toDomainEntity(document)));
        return details;
    }

    public List<MongoDBProductDetailDocument> domainEntitiesToMongoDocuments(ProductDetail[] details) {
        return Arrays.stream(details).map(this::toMongoDocument).toList();
    }
}
