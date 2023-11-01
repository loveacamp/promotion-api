package com.loveacamp.promotions.services;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;

import java.util.List;

public interface IPersonService {
    PersonDto save(PersonRequestDto personRequest);

    List<PersonDto> findAll();

    PersonDto update(long id, PersonRequestDto personRequest);

    PersonDto findById(long id);

    PersonDto delete(long id);
}
