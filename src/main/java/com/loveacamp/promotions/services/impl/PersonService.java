package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.UserDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.dto.requests.UserRequestDto;
import com.loveacamp.promotions.entities.User;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.UserRepository;
import com.loveacamp.promotions.services.IPersonService;
import com.loveacamp.promotions.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Override
    public PersonDto save(PersonRequestDto personRequest) {
        return null;
    }
}
