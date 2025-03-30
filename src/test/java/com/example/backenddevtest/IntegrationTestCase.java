package com.example.backenddevtest;

import com.example.backenddevtest.infrastructure.repository.MongoDBProductDetailJPARepository;
import com.example.backenddevtest.infrastructure.repository.MongoDBSimilarProductsJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestCase {
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.6");

    @LocalServerPort
    protected int port;

    @Autowired
    private MongoDBProductDetailJPARepository productDetailJPARepository;

    @Autowired
    private MongoDBSimilarProductsJPARepository similarProductsJPARepository;

    @BeforeEach
    public void beforeTest() {
        productDetailJPARepository.deleteAll();
        similarProductsJPARepository.deleteAll();
    }

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}
