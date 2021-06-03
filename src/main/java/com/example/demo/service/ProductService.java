package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductRequest productRequest);

    Product update(ProductRequest productRequest, String id);

    Product findById(String id);

    List<Product> findByParam(String name, Double minPrice, Double maxPrice);

    List<Product> findAll();

    void remove(String id);
}
