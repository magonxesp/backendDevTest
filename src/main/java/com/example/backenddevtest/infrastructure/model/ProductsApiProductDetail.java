package com.example.backenddevtest.infrastructure.model;

public class ProductsApiProductDetail {
    private String id;
    private String name;
    private double price;
    private boolean availability;

    public ProductsApiProductDetail() {
    }

    public ProductsApiProductDetail(String id, String name, double price, boolean availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }
}
