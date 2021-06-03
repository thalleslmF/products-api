package com.example.demo.repository.impl;

import com.example.demo.entity.Product;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.repository.extractor.ProductExtractor;
import com.example.demo.repository.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Repository

public class ProductRepositoryImpl implements ProductRepository {
    private JdbcTemplate jdbcTemplate;

    private final String BASE_QUERY_STATEMENT = "select id,name,description,price from products WHERE 1 = 1 ";

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {
        var statement = " INSERT INTO products(id,name,description,price) values (?,?,?,?)";
        this.jdbcTemplate.update(
            statement,
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
        return this.findById(product.getId()).orElseThrow(
                () -> new InternalServerErrorException("Failed to save product")
        );
    }

    @Override
    public Optional<Product> findById(String id) {
        var productList = this.jdbcTemplate.query(
                "select id,name,description,price from products where id = ?",
                new ProductExtractor(), id);
        return productList.isEmpty() ? Optional.empty() : Optional.of(productList.get(0));
    }

    @Override
    public List<Product> find() {
        return this.jdbcTemplate.query(
                this.BASE_QUERY_STATEMENT,
                new ProductExtractor());
    }

    @Override
    public void delete(String id) {
        this.jdbcTemplate.update(
                "DELETE FROM PRODUCTS WHERE id = ?",
                id
        );
    }

    @Override
    public Product update(Product product) {
        var statement = " UPDATE products set name = ?, description = ?, price = ? where id = ?";
        this.jdbcTemplate.update(
                statement,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getId()
        );
        return this.findById(product.getId()).orElseThrow(
                () -> new InternalServerErrorException("Unexpected error...")
        );
    }

    @Override
    public List<Product> find(String name, Double minPrice, Double maxPrice) {

        var queryParams = this.createQueryParams(Arrays.asList(name, name, minPrice, maxPrice));
        var queryStatement = new StringBuilder(this.BASE_QUERY_STATEMENT);
        var argTypes = this.crateArgTypes(queryParams);
        if (name != null) {
            queryStatement.append("AND (name = ? OR description = ?) ") ;
        }

        if (minPrice != null) {
            queryStatement.append("AND price >= ? ");
        }
        if (maxPrice != null) {
            queryStatement.append("AND price <= ? ");
        }
        return this.jdbcTemplate.query(
                queryStatement.toString(),
                queryParams.toArray(),
                argTypes.stream().mapToInt(i -> i).toArray(),
                new ProductExtractor()
        );
    }

    private ArrayList<Integer> crateArgTypes(List<? extends Serializable> queryParams) {
        var argTypes = new ArrayList<Integer>();
        for ( Object param: queryParams ) {
            if (param instanceof String) {
                argTypes.add(Types.VARCHAR);
            }
            if (param instanceof Double) {
                argTypes.add(Types.BIGINT);
            }
        }
        return argTypes;
    }

    private List<? extends Serializable> createQueryParams(List<? extends Serializable> queryParams) {
        return queryParams
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
