package com.example.demo.unit

import com.example.demo.entity.Product
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.ProductRepository
import com.example.demo.service.ProductService
import com.example.demo.service.ProductServiceImpl
import spock.lang.Specification

class ProductServiceTest extends Specification{
    ProductRepository productRepository = Mock(ProductRepository)
    ProductService productService = new ProductServiceImpl(productRepository);

    def 'should create a  product  with the correct args'() {
        given:
        def productRequest = TestUtils.productRequest
        when:
        this.productService.create(productRequest);

        then:

        1 * productRepository.save(_) >> { arguments ->
            def product = arguments[0]
            assert product instanceof Product
            assert product.name == productRequest.name
            assert product.description == productRequest.description
            assert productRequest.price == productRequest.price
        }
    }

    def 'when product does not exist should thrown exception'() {
        given:
        def productId = TestUtils.productId
        when:
        this.productService.findById(productId);

        then:
        1 * productRepository.findById(productId) >> Optional.empty()

        thrown(NotFoundException)
    }

    def 'should return a product successfully'() {
        given:
        def productId = TestUtils.productId
        def product = TestUtils.product
        when:
        this.productService.findById(productId);

        then:
        1 * productRepository.findById(productId) >> Optional.of(product)

        notThrown()
    }

    def 'should update a product with the correct args'() {
        given:
        def productId = TestUtils.productId
        def productRequest = TestUtils.productRequest

        when:
        this.productService.update( productRequest, productId);

        then:
        1 * productRepository.findById(productId) >> Optional.of(TestUtils.product)
        1 * productRepository.update(_) >>  { arguments ->
            def product = arguments[0]
            assert product instanceof Product
            assert product.name == productRequest.name
            assert product.description == productRequest.description
            assert product.price == productRequest.price
        }

        notThrown()
    }

    def 'when product does not exist, should not update and throw exception'() {
        given:
        def productId = TestUtils.productId
        def productRequest = TestUtils.productRequest

        when:
        this.productService.update( productRequest, productId);

        then:
        1 * productRepository.findById(productId) >> Optional.empty()
        0 * productRepository.update(_)

        thrown(NotFoundException)
    }

    def 'should delete a product with the correct args'() {
        given:
        def productId = TestUtils.productId

        when:
        this.productService.remove(productId);

        then:
        1 * productRepository.findById(productId) >> Optional.of(TestUtils.product)
        1 * productRepository.delete(_)

        notThrown()
    }

    def 'when product does not exists, should not delete and should thrown an exception'() {
        given:
        def productId = TestUtils.productId

        when:
        this.productService.remove(productId);

        then:
        1 * productRepository.findById(productId) >> Optional.empty()
        0 * productRepository.delete(_)

        thrown(NotFoundException)
    }
}
