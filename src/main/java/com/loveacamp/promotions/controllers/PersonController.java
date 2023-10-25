package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.services.IPersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/people", produces = "application/json;charset=UTF-8")
public class PersonController {
    private final IPersonService service;

    public PersonController(IPersonService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PersonDto> save(@Valid @RequestBody PersonRequestDto personRequest) {
        return ResponseEntity.ok(service.save(personRequest));
    }

//    @GetMapping
}
