package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.entities.Person;
import com.loveacamp.promotions.entities.Product;
import com.loveacamp.promotions.repositories.PersonRepository;
import com.loveacamp.promotions.utils.LessAndMoreCharacters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class PersonControllerTest extends AbstractControllerTest {
    @MockBean
    private PersonRepository repository;

    private PersonRequestDto personRequest;

    private final String less = LessAndMoreCharacters.less3Characters;
    private final String more = LessAndMoreCharacters.more255Characters;
    private final String invalidEmail = LessAndMoreCharacters.invalidEmail;

    @BeforeEach
    public void setup() {
        this.personRequest = createPersonRequestDto();
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto inválido, com nome nulo, retorne uma exceção.")
    public void givenPersonWhenSaveWithNameNullThenExpects400() throws Exception {
        personRequest.setName(null);

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"não deve estar em branco",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto inválido, com nome menor que 3 caracteres, retorne uma exceção.")
    public void givenPersonWhenSaveWithNameWithLessThan3CharactersThenExpects400() throws Exception {
        personRequest.setName(less);

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"tamanho deve ser entre 3 e 250",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto inválido, com nome maior que 255 caracteres, retorne uma exceção.")
    public void givenPersonWhenSaveWithNameWithMoreThan255CharactersThenExpects400() throws Exception {
        personRequest.setName(more);

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"tamanho deve ser entre 3 e 250",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto inválido, com email nulo, retorne uma exceção.")
    public void givenPersonWhenSaveWithEmailNullThenExpects400() throws Exception {
        personRequest.setEmail(null);

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message": "não deve estar em branco",
                                "field": "email"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto inválido, com email inválido, retorne uma exceção.")
    public void givenPersonWhenSaveWithInvalidEmailThenExpects400() throws Exception {
        personRequest.setEmail(invalidEmail);

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message": "deve ser um endereço de e-mail bem formado",
                                "field": "email"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("POST /api/people: Esperado que ao receber um dto válido, retorne uma pessoa")
    public void givenPersonWhenSaveThenPerson() throws Exception {
        when(this.repository.findByEmail(eq(this.personRequest.getEmail()))).thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createPerson());

        mockMvc.perform(post("/api/people").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.OK);
            JSONAssert.assertEquals("""
                    {
                        "id": 1,
                        "name": "John Doe",
                        "email": "john@email.com"
                    }
                    """, getContentAsString(result), false);
        });

        verify(this.repository, times(1)).findByEmail(eq(this.personRequest.getEmail()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto inválido, com nome nulo, retorne uma exceção.")
    public void givenPersonWhenUpdateWithNameNullThenExpects400() throws Exception {
        personRequest.setName(null);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"não deve estar em branco",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto inválido, com nome menor que 3 caracteres, retorne uma exceção.")
    public void givenPersonWhenUpdateWithNameWithLessThan3CharactersThenExpects400() throws Exception {
        personRequest.setName(less);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"tamanho deve ser entre 3 e 250",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto inválido, com nome maior que 255 caracteres, retorne uma exceção.")
    public void givenPersonWhenUpdateWithNameWithMoreThan255CharactersThenExpects400() throws Exception {
        personRequest.setName(more);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message":"tamanho deve ser entre 3 e 250",
                                "field":"name"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto inválido, com email nulo, retorne uma exceção.")
    public void givenPersonWhenUpdateWithEmailNullThenExpects400() throws Exception {
        personRequest.setEmail(null);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message": "não deve estar em branco",
                                "field": "email"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto inválido, com email inválido, retorne uma exceção.")
    public void givenPersonWhenUpdateWithInvalidEmailThenExpects400() throws Exception {
        personRequest.setEmail(invalidEmail);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.BAD_REQUEST);
            JSONAssert.assertEquals("""
                    {
                        "errors": [
                            {
                                "message": "deve ser um endereço de e-mail bem formado",
                                "field": "email"
                            }
                        ]
                    }
                    """, getContentAsString(result), false);
        });
    }

    @Test
    @DisplayName("PUT /api/people/{id}: Esperado que ao receber um dto válido, retorne uma pessoa")
    public void givenPersonWhenUpdateThenPerson() throws Exception {
        Person person = createPerson();

        when(this.repository.findById(eq(person.getId())))
                .thenReturn(Optional.of(person));
        when(this.repository.existsByEmailNotId(eq(this.personRequest.getEmail()), eq(person.getId())))
                .thenReturn(false);
        when(this.repository.save(argThat(this::checkArgs)))
                .thenReturn(person);

        mockMvc.perform(put("/api/people/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON).content(serializeInput(personRequest))).andDo(result -> {
            responseStatus(result, HttpStatus.OK);
            JSONAssert.assertEquals("""
                    {                                        
                        "id": 1,                    
                        "name": "John Doe",
                        "email": "john@email.com"
                    }
                    """, getContentAsString(result), false);
        });

        verify(this.repository, times(1)).findById(eq(person.getId()));
        verify(this.repository, times(1)).existsByEmailNotId(eq(this.personRequest.getEmail()), eq(person.getId()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("GET /api/people/{id}: Esperado que ao receber um id válido, retorne uma pessoa")
    public void givenPersonWhenFindByIdThenExpects200() throws Exception {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(this.createPerson()));

        mockMvc.perform(get(String.format("/api/people/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id": 1,
                                        "name": "John Doe",
                                        "email": "john@email.com"
                                    }
                                    """,
                            getContentAsString(result), true);
                });

        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("GET /api/people: Esperado que ao receber a chamada, retorne uma lista de pessoas")
    public void givenPersonWhenFindAllThenExpects200() throws Exception {
        when(this.repository.findAll())
                .thenReturn(List.of(createPerson(1L, "John Doe", "john@email.com"), createPerson(2L, "Joerta Joaquina", "joerta@email.com")));

        mockMvc.perform(get("/api/people")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    [
                                        {
                                            "id": 1,
                                            "name": "John Doe",
                                            "email": "john@email.com"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Joerta Joaquina",
                                            "email": "joerta@email.com"
                                        }
                                    ]
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("DELETE /api/people/{id}: Esperado que ao receber um id válido, retorne uma pessoa")
    public void givenPersonWhenDeleteThenExpects200() throws Exception {
        Long id = 1L;
        Person person = createPerson();

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(person));

        mockMvc.perform(delete(String.format("/api/people/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id": 1,
                                        "name": "John Doe",
                                        "email": "john@email.com"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).delete(eq(person));
        verifyNoMoreInteractions(this.repository);
    }


    private Person createPerson() {
        return new Person(1L, this.personRequest.getName(), this.personRequest.getEmail());
    }

    private Person createPerson(Long id, String name, String email) {
        return new Person(id, name, email);
    }

    private boolean checkArgs(Person person) {
        return person.getName().equals(this.personRequest.getName()) && person.getEmail().equals((this.personRequest.getEmail()));
    }

    private PersonRequestDto createPersonRequestDto() {
        return new PersonRequestDto("John Doe", "john@email.com");
    }
}