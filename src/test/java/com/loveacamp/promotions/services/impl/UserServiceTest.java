package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.UserDto;
import com.loveacamp.promotions.dto.requests.UserRequestDto;
import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.enums.UserLevel;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService service;

    @Mock
    private UserRepository repository;

    private UserRequestDto userRequestDto;

    @BeforeEach
    public void setup() {
        this.userRequestDto = createUserRequestDto();

        this.service = new UserService(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber um username existente, retorne uma exceção")
    public void givenExistsUserWhenSaveThenException() {
        when(this.repository.findByUsername(eq(this.userRequestDto.getUsername()))).thenReturn(Optional.of(mock(User.class)));

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.save(this.userRequestDto));

        assertThat(badRequestException).hasMessage("Usuário já existe.");
        verify(this.repository, times(1)).findByUsername(eq(this.userRequestDto.getUsername()));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber um username inexistente, retorne um usuário")
    public void givenNotExistsUserWhenSaveThenUser() {
        when(this.repository.findByUsername(eq(this.userRequestDto.getUsername()))).thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(createUser());

        UserDto userDto = this.service.save(this.userRequestDto);

        verify(this.repository, times(1)).findByUsername(eq(this.userRequestDto.getUsername()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);

        assertThat(userDto).hasToString("UserDto({id:1, username:John doe, level:ADMIN})");
    }

    @Test
    @DisplayName("update: Esperado que ao receber um username inexistente, retorne uma exceção")
    public void givenNotExistsUserWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id))).thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.userRequestDto));

        assertThat(badRequestException).hasMessage("Usuário não encontrado.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber um username já vinculado a outra conta, retorne uma exceção")
    public void givenUserAlreadyLinkedToAnotherAccountWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id))).thenReturn(Optional.of(createUser()));
        when(this.repository.existsByUsernameNotId(eq(userRequestDto.getUsername()),eq(id))).thenReturn(true);

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.userRequestDto));

        assertThat(badRequestException).hasMessage("Nome de usuário já vínculado a outra conta.");
        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).existsByUsernameNotId(eq(userRequestDto.getUsername()),eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber um username existente, sem vinculo com outra conta, retorne um usuário")
    public void givenUserWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id))).thenReturn(Optional.of(createUser()));
        when(this.repository.existsByUsernameNotId(eq(userRequestDto.getUsername()),eq(id))).thenReturn(false);
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(createUser());

        UserDto userDto =  this.service.update(id, this.userRequestDto);

        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).existsByUsernameNotId(eq(userRequestDto.getUsername()),eq(id));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);

        assertThat(userDto).hasToString("UserDto({id:1, username:John doe, level:ADMIN})");
    }

    @Test
    @DisplayName("findAll: Esperado que retorne os usuários existentes")
    public void givenUserWhenFindAllThenUsers() {
        when(this.repository.findAll())
                .thenReturn(List.of(createUser(1), createUser(2)));

        List<UserDto> usersDto = this.service.findAll();

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
        assertThat(usersDto).hasToString("[UserDto({id:1, username:John doe, level:ADMIN}), UserDto({id:2, username:John doe, level:ADMIN})]");
    }

    private boolean checkArgs(User user) {
        return user.getUsername().equals(this.userRequestDto.getUsername())
                && user.getPassword().equals(this.userRequestDto.getPassword())
                && user.getLevel().equals(this.userRequestDto.getLevel());
    }

    private UserRequestDto createUserRequestDto() {
        return new UserRequestDto("John doe", "password", UserLevel.ADMIN);
    }

    private User createUser() {
        return new User(1L, this.userRequestDto.getUsername(), this.userRequestDto.getPassword(), this.userRequestDto.getLevel());
    }

    private User createUser(long id) {
        User user = createUser();
        user.setId(id);

        return user;
    }
}