package io.github.backenddevtest.model;

public record ProductDetail(
        String id,
        String name,
        double price,
        boolean availability
) {}