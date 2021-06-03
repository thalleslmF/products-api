package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public ProductResponse create( @Valid @RequestBody ProductRequest productRequest) {
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id ) {
        this.productService.remove(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(
            @Valid @RequestBody ProductRequest productRequest,
            @PathVariable String id
    ) {
       return this.productService.update(productRequest, id);
    }
}
