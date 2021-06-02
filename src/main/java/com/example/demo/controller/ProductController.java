package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {
    ProductService productService;
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        return this.productService.create(productRequest).toResponse();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAll() {
        return this.productService.findAll().stream().map(
                Product::toResponse
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findById(@PathVariable String id ) {
        return this.productService.findById(id).toResponse();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findByParams(
            @RequestParam(name="q", required = false) String name,
            @RequestParam(name="min_price", required = false) Double minPrice,
            @RequestParam(name="max_price", required = false) Double maxPrice) {
        return this.productService.findByParam(name, minPrice, maxPrice).stream().map(
                Product::toResponse
        ).collect(Collectors.toList());
    }

}
