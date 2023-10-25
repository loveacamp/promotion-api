package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.ProductDto;
import com.loveacamp.promotions.dto.requests.ProductRequestDto;
import com.loveacamp.promotions.entities.Product;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.ProductRepository;
import com.loveacamp.promotions.services.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDto save(ProductRequestDto productRequestDto) {
        return ProductDto.toDto(this.repository.save(this.toEntity(productRequestDto)));
    }

    @Override
    public ProductDto update(Long id, ProductRequestDto productRequestDto) {
        Product product = this.toEntity(productRequestDto);
        product.setId(id);

        return ProductDto.toDto(this.repository.save(product));
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = this.existProduct(id);

        return ProductDto.toDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return ProductDto.toDto(this.repository.findAll());
    }

    @Override
    public ProductDto delete(Long id) {
        Product product = this.existProduct(id);

        this.repository.delete(product);

        return ProductDto.toDto(product);
    }

    private Product existProduct(Long id){
       return this.repository.findById(id).orElseThrow(() -> new BadRequestException("Produto não encontrado."));
    }

    private Product toEntity(ProductRequestDto productDto) {
        Product product = new Product();

        return product.setName(productDto.getName());
    }
}
