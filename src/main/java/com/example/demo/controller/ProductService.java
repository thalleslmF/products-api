package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product create(ProductRequest productRequest) {
        return this.productRepository.save(productRequest.toEntity()).orElseThrow(
                () -> new InternalServerErrorException("Failed to save product")
        );
    }

    public Product findById(String id) {
        return this.productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id)
        );
    }

    public List<Product> findAll() {
        return this.productRepository.find();
    }

    public List<Product> findByParam(String name, Double minPrice, Double maxPrice) {
        return this.productRepository.find(name, minPrice, maxPrice);
    }
}
