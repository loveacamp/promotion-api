package com.loveacamp.promotions.services;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;

public interface IPersonService {
    PersonDto save(PersonRequestDto personRequest);
}
