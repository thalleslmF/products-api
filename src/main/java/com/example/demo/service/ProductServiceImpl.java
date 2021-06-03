package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Product;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(ProductRequest productRequest) {
        return this.productRepository.save(productRequest.toEntity());
    }

    @Override
    public Product update(ProductRequest productRequest, String id) {
        var product = this.findById(id);
        return this.productRepository.update(productRequest.toEntity(product.getId()));
    }

    @Override
    public Product findById(String id) {
        return this.productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id)
        );
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.find();
    }

    @Override
    public void remove(String id) {
        var product = this.findById(id);
        this.productRepository.delete(product.getId());
    }

    @Override
    public List<Product> findByParam(String name, Double minPrice, Double maxPrice) {
        return this.productRepository.find(name, minPrice, maxPrice);
    }
}
