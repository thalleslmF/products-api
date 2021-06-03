package com.example.demo.integration


import com.example.demo.repository.ProductRepository
import com.example.demo.unit.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
