package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.UserDto;
import com.loveacamp.promotions.dto.requests.UserRequestDto;
import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.UserRepository;
import com.loveacamp.promotions.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto save(UserRequestDto userRequest) {
        if (repository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new BadRequestException("Usuário já existe.");
        }

        return UserDto.toDto(repository.save(toEntity(userRequest)));
    }

    @Override
    public UserDto update(long id, UserRequestDto userRequest) {
        User user = repository.findById(id).orElseThrow(() -> new BadRequestException("Usuário não encontrado."));

        if (repository.existsByUsernameNotId(userRequest.getUsername(), id)) {
            throw new BadRequestException("Nome de usuário já vínculado a outra conta.");
        }

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setLevel(userRequest.getLevel());

        return UserDto.toDto(repository.save(user));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.toDto(repository.findAll());
    }

    private User toEntity(UserRequestDto dto) {
        User user = new User();

        return user
                .setUsername(dto.getUsername())
                .setPassword(dto.getPassword())
                .setLevel(dto.getLevel());
    }
}
