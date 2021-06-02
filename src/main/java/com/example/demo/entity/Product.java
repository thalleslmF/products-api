package com.example.demo.entity;

import com.example.demo.dto.response.ProductResponse;

public class Product {

    private String id;
    private String description;
    private String name;
    private Double price;

    public Product(String id, String description, String name, Double price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public ProductResponse toResponse() {
        return new ProductResponse(
                this.id,
                this.description,
                this.name,
                this.price
        );
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
