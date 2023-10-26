package com.loveacamp.promotions.dto;

import com.loveacamp.promotions.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public ProductDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();

        return productDto
                .setId(product.getId())
                .setName(product.getName());
    }

    public static List<ProductDto> toDto(List<Product> products) {
        return products.stream().map(ProductDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("ProductDto({id:%s, name:%s})",
                this.getId(),
                this.getName()
        );
    }
}
