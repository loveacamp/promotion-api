package com.loveacamp.promotions.repositories;

import com.loveacamp.promotions.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    Optional<Product> findByIdAndName(Long id, String name);
}
