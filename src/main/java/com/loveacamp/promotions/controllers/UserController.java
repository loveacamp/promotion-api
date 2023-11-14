package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.UserDto;
import com.loveacamp.promotions.dto.requests.UserRequestDto;
import com.loveacamp.promotions.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json;charset=UTF-8")
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserRequestDto user) {
        return ResponseEntity.ok(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") long id, @Valid @RequestBody UserRequestDto user) {
        return ResponseEntity.ok(service.update(id, user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
