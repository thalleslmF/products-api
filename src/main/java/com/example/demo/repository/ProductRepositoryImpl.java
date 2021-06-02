package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
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
    public Optional<Product> save(Product product) {
        System.out.println(product);
        var statement = " INSERT INTO products(id,name,description,price) values (?,?,?,?)";
        this.jdbcTemplate.update(
            statement,
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
        return this.findById(product.getId());
    }

    @Override
    public Optional<Product> findById(String id) {
        System.out.println(id);
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
    public void delete() {

    }

    @Override
    public Product update() {
        return null;
    }

    @Override
    public List<Product> find(String name, Double minPrice, Double maxPrice) {
        var queryParam = Arrays.asList(name, name, minPrice, maxPrice);
        var findQueryStatement = new StringBuilder(this.BASE_QUERY_STATEMENT);
        var queryParamFiltered = queryParam
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        var argTypes = new ArrayList<Integer>();
        if (name != null) {
            findQueryStatement.append("AND (name = ? OR description = ?) ") ;
            argTypes.add(Types.VARCHAR);
            argTypes.add(Types.VARCHAR);
        }

        if (minPrice != null) {
            findQueryStatement.append("AND price >= ? ");
            argTypes.add(Types.BIGINT);
        }
        if (maxPrice != null) {
            findQueryStatement.append("AND price <= ? ");
            argTypes.add(Types.BIGINT);
        }
        return this.jdbcTemplate.query(
                findQueryStatement.toString(),
                queryParamFiltered.toArray(),
                argTypes.stream().mapToInt(i -> i).toArray(),
                new ProductExtractor()
        );
    }
}
