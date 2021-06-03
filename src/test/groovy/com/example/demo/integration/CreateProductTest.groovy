package com.example.demo.integration

import com.example.demo.dto.request.ProductRequest
import com.example.demo.dto.response.ProductResponse
import com.example.demo.exception.handler.ErrorMessageResponse
import com.example.demo.repository.ProductRepository
import com.example.demo.unit.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.relational.core.sql.SQL
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateProductTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository;
    def "when it is a invalid product should not create "() {
        given:
        def notValidProductRequest = TestUtils.notValidProductRequest()

        when:
            def result = restTemplate.postForEntity("/products", notValidProductRequest, ErrorMessageResponse.class)
        then:
            assert result.body.code == 400
            assert result.statusCode == HttpStatus.BAD_REQUEST
    }
    def "when it is a valid product should create successfully"() {
        given:
        def productRequest = TestUtils.productRequest

        when:
        def result = restTemplate.postForEntity("/products", productRequest, ProductResponse.class)
        then:
        assert result.body.description == productRequest.description
        assert result.body.name == productRequest.name
        assert result.body.price == productRequest.price
    }


}
