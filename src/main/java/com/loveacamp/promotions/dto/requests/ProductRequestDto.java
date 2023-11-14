package com.loveacamp.promotions.dto.requests;

import jakarta.validation.constraints.*;

public class ProductRequestDto{
    @NotBlank
    @Size(min = 3, max = 250)
    private String name;

    public String getName() {
        return name;
    }

    public ProductRequestDto setName(String name) {
        this.name = name;
        return this;
    }
}
