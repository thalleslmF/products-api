package com.example.demo.unit

import com.example.demo.dto.request.ProductRequest
import com.example.demo.entity.Product

class TestUtils {

    static ProductRequest getProductRequest() {
        def productRequest = new ProductRequest()
        productRequest.setName("dummy-name")
        productRequest.setDescription("dummy-description")
        productRequest.setPrice(20.0)
        return productRequest
    }
    static String getProductId() {
        return UUID.randomUUID().toString()
    }

    static Product getProduct() {
        def product = new Product()
        product.setName("dummy-name")
        product.setDescription("dummy-description")
        product.setPrice(20.0)
        product.setId(productId)
        return product
    }

    static ProductRequest notValidProductRequest() {
        def productRequest = new ProductRequest()
        productRequest.setName("")
        productRequest.setDescription("")
        productRequest.setPrice(-5)
        return productRequest
    }
}
