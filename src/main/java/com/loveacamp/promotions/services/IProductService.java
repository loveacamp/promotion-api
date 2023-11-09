package com.loveacamp.promotions.services;

import com.loveacamp.promotions.dto.ProductDto;
import com.loveacamp.promotions.dto.requests.ProductRequestDto;

import java.util.List;

public interface IProductService {
    ProductDto save(ProductRequestDto productRequestDto);

    ProductDto update(Long id, ProductRequestDto productRequestDto);

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    ProductDto delete(Long id);
}
