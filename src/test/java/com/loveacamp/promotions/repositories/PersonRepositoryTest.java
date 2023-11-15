package com.loveacamp.promotions.repositories;

import com.loveacamp.promotions.entities.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest extends AbstractRepository {
    @Autowired
    private PersonRepository repository;

    @Test
    @DisplayName("findById: Esperado qua ao buscar uma pessoa inexistente, retorne vazio")
    public void givenNotExistsPersonWhenFindByIdThenVoid() {
        Long id = 1L;

        Optional<Person> result = repository.findById(id);

        assertFalse(result.isPresent());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findById: Esperado qua ao buscar uma pessoa existente, retorne a pessoa")
    public void givenPersonWhenFindByIdThenPerson() {
        Long id = 1L;

        Optional<Person> result = repository.findById(id);

        assertFalse(result.isPresent());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByEmail: Esperado ao receber um email inexistente a uma pessoa, retorne vazio")
    public void givenPersonNotExistsEmailWhenFindByEmailThenVoid() {
        String name = "John Doe";
        String email = "john@email.com";

        Person person = createPerson(name, email);
        repository.save(person);

        Optional<Person> result = repository.findByEmail(person.getEmail());
        Person foundPerson = result.get();

        assertTrue(result.isPresent());
        assertPerson(foundPerson, name, email);
    }

    @Test
    @DisplayName("findByEmail: Esperado ao receber um email pertencente a uma pessoa, retorne a pessoa")
    public void givenPersonExistsEmailWhenFindByEmailThenPerson() {
        String name = "John Doe";
        String email = "john@email.com";

        Person person = createPerson(name, email);
        repository.save(person);

        Optional<Person> result = repository.findByEmail(person.getEmail());
        Person foundPerson = result.get();

        assertTrue(result.isPresent());
        assertPerson(foundPerson, name, email);
    }

    @Test
    @DisplayName("findAll: Esperado que retorne todas pessoas existentes")
    public void givenPersonWhenFindAllThenPeople() {
        repository.save(createPerson("John Doe", "john@email.com"));
        repository.save(createPerson("Maria Joaquina", "maria_j@email.com"));
        repository.save(createPerson("Joaquim Emanuel", "joaquim.e@email.com"));

        List<Person> result = repository.findAll();

        assertEquals(result.size(), 3);
    }

    @Test
    @DisplayName("existsByEmailNotId: Esperado que ao receber um email que nao pertence a mesma pessoa, retorne verdadeiro")
    public void givenPersonEmailExistsInOtherWhenExistsByEmailNotIdThenTrue() {
        Person person1 = repository.save(createPerson("John Doe", "john@email.com"));
        Person person2 = repository.save(createPerson("Maria Joaquina", "maria_j@email.com"));

        boolean existsOtherPersonWithSameEmail = repository.existsByEmailNotId(person1.getEmail(), person2.getId());

        assertTrue(existsOtherPersonWithSameEmail);
    }

    @Test
    @DisplayName("existsByEmailNotId: Esperado que ao receber um email inexistente, retorne falso")
    public void givenPersonEmailNotExistsInOtherWhenExistsByEmailNotIdThenFalse() {
        Person person = repository.save(createPerson("John Doe", "john@email.com"));

        boolean existsOtherPersonWithSameEmail = repository.existsByEmailNotId(person.getEmail(), person.getId());

        assertFalse(existsOtherPersonWithSameEmail);
    }

    @Test
    @DisplayName("delete")
    public void givenPersonWhenDeleteThenPerson() {
        Long id = 1L;
        Person person = createPerson("John Doe", "john@email.com");
        repository.save(person);

        Optional<Person> result1 = repository.findById(id);
        assertTrue(result1.isPresent());

        repository.delete(person);

        Optional<Person> result2 = repository.findById(id);
        assertFalse(result2.isPresent());
    }

    private void assertPerson(Person person, String name, String email) {
        assertThat(person)
                .extracting(Person::getId, Person::getName, Person::getEmail)
                .containsExactly(1L, name, email);
    }

    private Person createPerson(String name, String email) {
        return new Person(null, name, email);
    }
}