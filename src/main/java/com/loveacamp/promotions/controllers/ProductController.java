package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.ProductDto;
import com.loveacamp.promotions.dto.requests.ProductRequestDto;
import com.loveacamp.promotions.services.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products", produces = "application/json;charset=UTF-8")
public class ProductController {
    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(service.save(productRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(service.update(id, productRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
