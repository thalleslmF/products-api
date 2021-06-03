package com.example.demo.integration

import com.example.demo.dto.request.ProductRequest
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
class UpdateProductTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository;
    def "should update a existing product"() {
        given:
        def product = TestUtils.product
        def productUpdateRequest = new ProductRequest();
        productUpdateRequest.name = 'another-name'
        productUpdateRequest.description = 'another-description'
        productUpdateRequest.price = 60
        this.productRepository.save(product)
        def url  = String.format("/products/%s",product.id)
        when:
            restTemplate.put(url,productUpdateRequest)
        then:
            def result = productRepository.findById(product.getId())
            assert result.isPresent()
            assert result.get().name == productUpdateRequest.name
            assert result.get().description == productUpdateRequest.description
            assert result.get().price == productUpdateRequest.price
    }

}
