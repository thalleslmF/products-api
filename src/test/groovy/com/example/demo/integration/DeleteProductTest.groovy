package com.example.demo.integration


import com.example.demo.dto.response.ProductResponse
import com.example.demo.exception.handler.ErrorMessageResponse
import com.example.demo.repository.ProductRepository
import com.example.demo.unit.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteProductTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository;
    def "should delete a existing product"() {
        given:
        def product = TestUtils.product
        this.productRepository.save(product)
        def url  = String.format("/products/%s",product.id)
        when:
            restTemplate.delete(url)
        then:
            def result = productRepository.findById(product.getId())
            assert result.isEmpty()
    }

}
