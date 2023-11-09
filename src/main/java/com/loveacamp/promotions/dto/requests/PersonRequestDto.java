package com.loveacamp.promotions.dto.requests;

import jakarta.validation.constraints.*;

public class PersonRequestDto {
    @NotBlank
    @Size(min = 3, max = 250)
    private String name;

    @NotBlank
    @Email
    private String email;

    public PersonRequestDto() {
    }

    public PersonRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public PersonRequestDto setName(String name) {
        this.name = name;

        return this;
    }

    public String getEmail() {
        return email;
    }

    public PersonRequestDto setEmail(String email) {
        this.email = email;

        return this;
    }
}
