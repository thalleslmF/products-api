package com.example.demo.dto.request;

import com.example.demo.entity.Product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class ProductRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private Double price;

    public Product toEntity() {
        return new Product(
                UUID.randomUUID().toString(),
                this.description,
                this.name,
                this.price
        );
    }

    public Product toEntity(String id) {
        return new Product(
                id,
                this.description,
                this.name,
                this.price
        );
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
