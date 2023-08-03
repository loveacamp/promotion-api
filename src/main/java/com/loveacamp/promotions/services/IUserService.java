package com.loveacamp.promotions.services;

import com.loveacamp.promotions.dto.UserDto;
import com.loveacamp.promotions.dto.requests.UserRequestDto;

import java.util.List;

public interface IUserService {
    UserDto save(UserRequestDto user);

    List<UserDto> findAll();

    UserDto update(long id, UserRequestDto user);

}
