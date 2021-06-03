package com.example.demo.integration

import com.example.demo.dto.response.ProductResponse
import com.example.demo.entity.Product
import com.example.demo.repository.ProductRepository
import com.example.demo.unit.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetProductTest extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository;
    def "when there is a  product on database, should return it on the get /products list response"() {
        given:
        def productCompare = TestUtils.product
        productRepository.save(productCompare)

        when:
            def result = restTemplate.getForObject("/products", ProductResponse[].class)
        then:
            assert result[0].description == productCompare.description
            assert result[0].name == productCompare.name
            assert result[0].price == productCompare.price

    }

    def "when there is no product matching the search parameters should return empty list"() {
        given:
        def searchParameter = 'min_price=30'
        def productCompare = TestUtils.product
        productRepository.save(productCompare)
        def url = String.format("/products/search?%s",searchParameter)
        when:
        def result = restTemplate.getForObject(url, ProductResponse[].class)
        then:
        assert result.size() == 0

    }

    def "when there is a product matching the search parameter should return the product"() {
        given:
        def searchParameter = 'min_price=20'
        def productCompare = TestUtils.product
        def url = String.format("/products/search?%s",searchParameter)
        productRepository.save(productCompare)
        when:
        def result = restTemplate.getForObject(url, ProductResponse[].class)
        then:
        assert result[0].description == productCompare.description
        assert result[0].name == productCompare.name
        assert result[0].price == productCompare.price

    }

    def "when a product match the search parameters by name but not by price, should not return"() {
        given:
        def productCompare = TestUtils.product
        def searchParameter = 'q=dummy-name&max_price=15'
        def url = String.format("/products/search?%s",searchParameter)
        productRepository.save(productCompare)
        when:
        def result = restTemplate.getForObject(url, ProductResponse[].class)
        then:
        assert result.size() == 0

    }

    def "when a product match the search parameters by name and by price, should  return"() {
        given:
        def productCompare = TestUtils.product
        def searchParameter = 'q=dummy-name&max_price=30'
        def url = String.format("/products/search?%s",searchParameter)
        productRepository.save(productCompare)
        when:
        def result = restTemplate.getForObject(url, ProductResponse[].class)
        then:
        assert result[0].description == productCompare.description
        assert result[0].name == productCompare.name
        assert result[0].price == productCompare.price

    }


}
