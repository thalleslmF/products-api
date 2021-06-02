package com.example.demo.dto.response;

public class ProductResponse {

    private String id;
    private String description;
    private String name;
    private Double price;

    public ProductResponse(String id, String description, String name, Double price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
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
}
