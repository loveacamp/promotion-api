package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.requests.UserRequestDto;
import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.enums.UserLevel;
import com.loveacamp.promotions.repositories.UserRepository;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class UserControllerTest extends AbstractControllerTest {
    @MockBean
    private UserRepository repository;

    private UserRequestDto userRequestDto;

    private final String less = LessAndMoreCharacters.less3Characters;

    private final String more = LessAndMoreCharacters.more255Characters;

    @BeforeEach
    public void setup() {
        this.userRequestDto = this.createUserRequestDto();
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username nulo, retorne uma exceção")
    public void givenUsersWhenSaveWithUsernameNullThenExpects400() throws Exception {
        userRequestDto.setUsername(null);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username menor que 3 caracteres, retorne uma exceção")
    public void givenUsersWhenSaveWithUsernameWithLessThan3CharactersThenExpects400() throws Exception {
        userRequestDto.setUsername(less);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username maior que 255 caracteres, retorne uma exceção")
    public void givenUsersWhenSaveWithUsernameWithMoreThan255CharactersThenExpects400() throws Exception {
        userRequestDto.setUsername(more);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username nulo, retorne uma exceção")
    public void givenUsersWhenSaveWithPasswordNullThenExpects400() throws Exception {
        userRequestDto.setPassword(null);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username menor que 3 caracteres, retorne uma exceção")
    public void givenUsersWhenSaveWithPasswordWithLessThan3CharactersThenExpects400() throws Exception {
        userRequestDto.setPassword(less);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com username maior que 255 caracteres, retorne uma exceção")
    public void givenUsersWhenSaveWithPasswordWithMoreThan255CharactersThenExpects400() throws Exception {
        userRequestDto.setPassword(more);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto inválido, com level nulo, retorne uma exceção")
    public void givenUsersWhenSaveWithLevelNullThenExpects400() throws Exception {
        userRequestDto.setLevel(null);

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "level",
                                                "message": "não deve ser nulo"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/users: Esperado que ao receber um dto válido, retorne um usuário")
    public void givenUsersWhenSaveThenExpects200() throws Exception {
        when(this.repository.findByUsername(eq(this.userRequestDto.getUsername())))
                .thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs)))
                .thenReturn(createUser());

        mockMvc.perform(post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id":1,
                                        "username":"John Doe",
                                        "level":"USER"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findByUsername(eq(this.userRequestDto.getUsername()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username nulo, retorne uma exceção")
    public void givenUsersWhenUpdateWithUsernameNullThenExpects400() throws Exception {
        userRequestDto.setUsername(null);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username menor que 3 caracteres, retorne uma exceção")
    public void givenUsersWhenUpdateWithUsernameWithLessThan3CharactersThenExpects400() throws Exception {
        userRequestDto.setUsername(less);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username maior que 255 caracteres, retorne uma exceção")
    public void givenUsersWhenUpdateWithUsernameWithMoreThan255CharactersThenExpects400() throws Exception {
        userRequestDto.setUsername(more);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "username",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username nulo, retorne uma exceção")
    public void givenUsersWhenUpdateWithPasswordNullThenExpects400() throws Exception {
        userRequestDto.setPassword(null);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username menor que 3 caracteres, retorne uma exceção")
    public void givenUsersWhenUpdateWithPasswordWithLessThan3CharactersThenExpects400() throws Exception {
        userRequestDto.setPassword(less);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com username maior que 255 caracteres, retorne uma exceção")
    public void givenUsersWhenUpdateWithPasswordWithMoreThan255CharactersThenExpects400() throws Exception {
        userRequestDto.setPassword(more);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "password",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto inválido, com level nulo, retorne uma exceção")
    public void givenUsersWhenUpdateWithLevelNullThenExpects400() throws Exception {
        userRequestDto.setLevel(null);

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "level",
                                                "message": "não deve ser nulo"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/users/{id}: Esperado que ao receber um dto válido, retorne um usuário")
    public void givenUsersWhenUpdateThenExpects200() throws Exception {
        Long id = 1L;

        when(this.repository.findById(eq(id))).thenReturn(Optional.of(createUser()));
        when(this.repository.existsByUsernameNotId(eq(this.userRequestDto.getUsername()),eq(id))).thenReturn(false);
        when(this.repository.save(argThat(this::checkArgs)))
                .thenReturn(createUser());

        mockMvc.perform(put("/api/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id":1,
                                        "username":"John Doe",
                                        "level":"USER"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).existsByUsernameNotId(eq(this.userRequestDto.getUsername()), eq(id));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("GET /api/users: Esperado que ao receber a chamada, retorne uma lista de usuários")
    public void givenUsersWhenFindAllThenExpects200() throws Exception {
        when(this.repository.findAll())
                .thenReturn(List.of(createUser(1L, "John Doe"), createUser(2L, "Carla Doe")));

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(userRequestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    [
                                        {
                                            "id":1,
                                            "username":"John Doe",
                                            "level":"USER"
                                        },
                                        {
                                            "id":2,
                                            "username":"Carla Doe",
                                            "level":"USER"
                                        }
                                    ]
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    private boolean checkArgs(User user) {
        return user.getUsername().equals(this.userRequestDto.getUsername())
                && user.getPassword().equals(this.userRequestDto.getPassword())
                && user.getLevel().equals(this.userRequestDto.getLevel());
    }

    private UserRequestDto createUserRequestDto() {
        return new UserRequestDto("John Doe", "123", UserLevel.USER);
    }

    private User createUser() {
        return new User(1L, this.userRequestDto.getUsername(), this.userRequestDto.getPassword(), this.userRequestDto.getLevel());
    }

    private User createUser(long id, String username) {
        User user = createUser();
        user.setId(id);
        user.setUsername(username);

        return user;
    }
}