package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.entities.Person;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.PersonRepository;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    private PersonService service;

    @Mock
    private PersonRepository repository;

    PersonRequestDto personRequest;

    @BeforeEach
    public void setup() {
        this.personRequest = createPersonRequestDto();

        this.service = new PersonService(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber uma pessoa existente, retorne uma exceção")
    public void givenExistsPersonWhenSaveThenException() {
        when(this.repository.findByEmail(eq(this.personRequest.getEmail()))).thenReturn(Optional.of(mock(Person.class)));

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.save(this.personRequest));

        assertThat(badRequestException).hasMessage("Email já esta vinculado a outra conta.");
        verify(this.repository, times(1)).findByEmail(eq(this.personRequest.getEmail()));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber uma pessoa inexistente, retorne uma pessoa")
    public void givenNotExistsPersonWhenSaveThenPerson() throws JSONException {
        when(this.repository.findByEmail(eq(this.personRequest.getEmail()))).thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createPerson());

        PersonDto personDto = this.service.save(this.personRequest);

        verify(this.repository, times(1)).findByEmail(eq(this.personRequest.getEmail()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);

        assertThat(personDto).hasToString("PersonDto({id:1, name:John Doe, email:john@email.com})");
    }

    @Test
    @DisplayName("findAll: Esperado que retorne as pessoas existentes")
    public void givenPeopleWhenFindAllThenPeople() {
        when(this.repository.findAll())
                .thenReturn(List.of(createPerson(1L, "John Doe", "john@email.com"), createPerson(2L, "Joerta Joaquina", "joerta@email.com")));

        List<PersonDto> personDto = this.service.findAll();

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
        assertThat(personDto).hasToString("[PersonDto({id:1, name:John Doe, email:john@email.com}), PersonDto({id:2, name:Joerta Joaquina, email:joerta@email.com})]");
    }

    @Test
    @DisplayName("findById: Esperado que ao receber uma pessoa inexistente, retorne uma exceção")
    public void givenNotExistsPersonWhenFindByIdThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.findById(id));

        assertThat(badRequestException).hasMessage("Pessoa não encontrada.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("findById: Esperado que ao receber uma pessoa existente, retorne uma pessoa")
    public void givenExistsPersonWhenFindByIdThenPerson() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(createPerson()));

        PersonDto personDto = this.service.findById(id);

        assertThat(personDto).hasToString("PersonDto({id:1, name:John Doe, email:john@email.com})");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber uma pessoa inexistente, retorne uma exceção")
    public void givenNotExistsPersonWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.personRequest));

        assertThat(badRequestException).hasMessage("Pessoa não encontrada.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber uma pessoa com email existente em outra pessoa, retorne uma exceção")
    public void givenEmailExistingOfPersonWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(createPerson()));
        when(this.repository.existsByEmailNotId(eq(this.personRequest.getEmail()), eq(id)))
                .thenReturn(true);

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.personRequest));

        assertThat(badRequestException).hasMessage("Email já esta vinculado a outra conta.");
        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).existsByEmailNotId(eq(this.personRequest.getEmail()), eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber uma pessoa existente, retorne uma pessoa")
    public void givenExistsPersonWhenUpdateThenPerson() {
        Person person = createPerson();

        when(this.repository.findById(eq(person.getId())))
                .thenReturn(Optional.of(person));
        when(this.repository.existsByEmailNotId(eq(this.personRequest.getEmail()), eq(person.getId())))
                .thenReturn(false);
        when(this.repository.save(argThat(this::checkArgs)))
                .thenReturn(person);

        PersonDto personDto = this.service.update(person.getId(), this.personRequest);

        assertThat(personDto).hasToString("PersonDto({id:1, name:John Doe, email:john@email.com})");
        verify(this.repository, times(1)).findById(eq(person.getId()));
        verify(this.repository, times(1)).existsByEmailNotId(eq(this.personRequest.getEmail()), eq(person.getId()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber uma pessoa inexistente, retorne uma exceção")
    public void givenNotExistsPersonWhenDeleteThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.delete(id));

        assertThat(badRequestException).hasMessage("Pessoa não encontrada.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber uma pessoa existente, retorne uma pessoa")
    public void givenExistsPersonWhenDeleteThenPerson () {
        Long id = 1L;
        Person person = createPerson();

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(person));

        PersonDto personDto = this.service.delete(id);

        assertThat(personDto).hasToString("PersonDto({id:1, name:John Doe, email:john@email.com})");
        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).delete(eq(person));
        verifyNoMoreInteractions(this.repository);
    }


    private boolean checkArgs(Person person) {
        return person.getName().equals(this.personRequest.getName()) && person.getEmail().equals((this.personRequest.getEmail()));
    }

    private Person createPerson() {
        return new Person(1L, this.personRequest.getName(), this.personRequest.getEmail());
    }

    private Person createPerson(Long id, String name, String email) {
        return new Person(id, name, email);
    }

    private PersonRequestDto createPersonRequestDto() {
        PersonRequestDto personRequest = new PersonRequestDto();

        return personRequest.setName("John Doe").setEmail("john@email.com");
    }

}