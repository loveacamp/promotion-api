package com.loveacamp.promotions.dto;

import com.loveacamp.promotions.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDto {
    private Long id;

    private String name;

    private String email;

    public Long getId() {
        return id;
    }

    public PersonDto setId(Long id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public PersonDto setName(String name) {
        this.name = name;

        return this;
    }

    public String getEmail() {
        return email;
    }

    public PersonDto setEmail(String email) {
        this.email = email;

        return this;
    }

    public static PersonDto toDto(Person person) {
        PersonDto personDto = new PersonDto();

        return personDto
                .setId(person.getId())
                .setName(person.getName())
                .setEmail(person.getEmail());
    }

    public static List<PersonDto> toDto(List<Person> people) {
        return people.stream().map(PersonDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("PersonDto({id:%s, name:%s, email:%s})",
                this.getId(),
                this.getName(),
                this.getEmail()
        );
    }
}
