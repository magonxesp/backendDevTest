package com.example.backenddevtest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest
public abstract class IntegrationTestCase {
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.6");

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}
