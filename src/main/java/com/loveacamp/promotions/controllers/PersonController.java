package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.PersonDto;
import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.services.IPersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/people", produces = "application/json;charset=UTF-8")
public class PersonController {
    private final IPersonService service;

    public PersonController(IPersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<PersonDto> save(@Valid @RequestBody PersonRequestDto personRequest) {
        return ResponseEntity.ok(service.save(personRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable("id") long id, @Valid @RequestBody PersonRequestDto person) {
        return ResponseEntity.ok(service.update(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDto> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
