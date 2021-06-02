package com.example.demo.repository;

import com.example.demo.entity.Product;
import java.util.List;
import java.util.Optional;
public interface ProductRepository {
    Optional<Product> save(Product product);
    Optional<Product> findById(String id);
    List<Product> find();
    void delete();
    Product update();
    List<Product> find(String name, Double minPrice, Double maxPrice);
}
