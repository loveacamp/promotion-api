package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.entities.Person;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.PersonRepository;
import com.loveacamp.promotions.services.IPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public PersonDto save(PersonRequestDto personRequest) {
        if (repository.findByEmail(personRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email já esta vinculado a outra conta.");
        }

        return PersonDto.toDto(repository.save(toEntity(personRequest)));
    }

    @Override
    public List<PersonDto> findAll() {
        return PersonDto.toDto(repository.findAll());
    }

    @Override
    public PersonDto findById(long id) {
        return PersonDto.toDto(getPersonFromRepositoryById(id));
    }

    @Override
    public PersonDto update(long id, PersonRequestDto personRequest) {
        Person person = getPersonFromRepositoryById(id);

        if (repository.existsByEmailNotId(personRequest.getEmail(), id)) {
            throw new BadRequestException("Este e-mail já está em uso.");
        }

        person.setName(person.getName());
        person.setEmail(personRequest.getEmail());

        return PersonDto.toDto(repository.save(person));
    }

    @Override
    public PersonDto delete(long id) {
        Person person = getPersonFromRepositoryById(id);
        repository.delete(person);
        return PersonDto.toDto(person);
    }

    private Person getPersonFromRepositoryById(long id) {
        return repository.findById(id).orElseThrow(
                () -> new BadRequestException("Pessoa não encontrada.")
        );
    }

    private Person toEntity(PersonRequestDto dto) {
        Person person = new Person();

        return person
                .setName(dto.getName())
                .setEmail(dto.getEmail());
    }
}
