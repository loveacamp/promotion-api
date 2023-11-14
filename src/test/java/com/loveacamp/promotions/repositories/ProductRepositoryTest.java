package com.loveacamp.promotions.repositories;

import com.loveacamp.promotions.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest extends AbstractRepository {
    @Autowired
    private ProductRepository repository;

    @Test
    @DisplayName("findById")
    public void givenProductWhenFindByIdThenProduct() {
        Long id = 1L;
        Product product = createEntity("Pizza");
        repository.save(product);

        Optional<Product> result = repository.findById(id);

        assertTrue(result.isPresent());
        Product foundProduct = result.get();
        assertProduct(foundProduct, "Pizza");
    }

    @Test
    @DisplayName("findByName")
    public void givenProductWhenFindByNameThenProduct() {
        Product product = createEntity("Pizza de Marguerita");
        repository.save(product);

        Optional<Product> result = repository.findByName(product.getName());

        assertTrue(result.isPresent());
        Product foundProduct = result.get();
        assertProduct(foundProduct, "Pizza de Marguerita");
    }

    @Test
    @DisplayName("findByIdAndName")
    public void givenProductWhenFindByIdAndNameThenProduct() {
        Product product = createEntity("Pizza de Marguerita");
        repository.save(product);

        Optional<Product> result = repository.findByIdAndName(1L, product.getName());

        assertTrue(result.isPresent());
        Product foundProduct = result.get();
        assertProduct(foundProduct, "Pizza de Marguerita");
    }

    @Test
    @DisplayName("findAll")
    public void givenProductWhenFindAllThenProduct() {
        repository.save(createEntity("Pizza"));
        repository.save(createEntity("Pastel"));
        repository.save(createEntity("Doce de Abóbora"));

        List<Product> result = repository.findAll();

        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("delete")
    public void givenProductWhenDeleteThenProduct() {
        Long id = 1L;
        Product product = createEntity("Pizza");
        repository.save(product);

        Optional<Product> result1 = repository.findById(id);
        assertTrue(result1.isPresent());

        repository.delete(product);

        Optional<Product> result2 = repository.findById(id);
        assertFalse(result2.isPresent());
    }

    private void assertProduct(Product product, String name) {
        assertEquals((Long) 1L, product.getId());
        assertEquals(name, product.getName());
    }

    private Product createEntity(String name) {
        return new Product(null, name);
    }
}