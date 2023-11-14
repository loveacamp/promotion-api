package com.loveacamp.promotions.services.impl;

import com.loveacamp.promotions.dto.ProductDto;
import com.loveacamp.promotions.dto.requests.ProductRequestDto;
import com.loveacamp.promotions.entities.Product;
import com.loveacamp.promotions.exception.BadRequestException;
import com.loveacamp.promotions.repositories.ProductRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService service;

    @Mock
    private ProductRepository repository;

    ProductRequestDto productRequest;

    @BeforeEach
    public void setup() {
        this.productRequest = createProductRequestDto();

        this.service = new ProductService(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber um produto existente, retorne uma exceção")
    public void givenExistsProductWhenSaveThenException() {
        when(this.repository.findByName(eq(this.productRequest.getName())))
                .thenReturn(Optional.of(mock(Product.class)));

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.save(this.productRequest));

        assertThat(badRequestException).hasMessage("Já existe um produto cadastrado com este nome.");
        verify(this.repository, times(1)).findByName(eq(this.productRequest.getName()));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("save: Esperado que ao receber um produto inexistente, retorne um produto")
    public void givenNotExistsProductWhenSaveThenProduct() throws JSONException {
        when(this.repository.findByName(eq(this.productRequest.getName())))
                .thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createProduct());

        ProductDto productDto = this.service.save(this.productRequest);

        verify(this.repository, times(1)).findByName(eq(this.productRequest.getName()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);

        assertThat(productDto).hasToString("ProductDto({id:1, name:Pizza de Banana})");
    }

    @Test
    @DisplayName("update: Esperado que ao receber um produto existente, retorne uma exceção")
    public void givenExistsProductWhenUpdateThenException() {
        Long id = 1L;

        when(this.repository.findByIdAndName(eq(id), eq(this.productRequest.getName())))
                .thenReturn(Optional.of(mock(Product.class)));

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.update(id, this.productRequest));

        assertThat(badRequestException).hasMessage("Já existe um produto cadastrado com este nome.");
        verify(this.repository, times(1)).findByIdAndName(eq(id), eq(this.productRequest.getName()));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("update: Esperado que ao receber um produto inexistente, retorne um produto")
    public void givenExistsProductWhenUpdateThenProduct() {
        Long id = 1L;

        when(this.repository.findByIdAndName(eq(id), eq(this.productRequest.getName())))
                .thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createProduct());

        ProductDto productDto = this.service.update(id, this.productRequest);

        verify(this.repository, times(1)).findByIdAndName(eq(id), eq(this.productRequest.getName()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);

        assertThat(productDto).hasToString("ProductDto({id:1, name:Pizza de Banana})");
    }

    @Test
    @DisplayName("findById: Esperado que ao receber um produto existente, retorne uma exceção")
    public void givenExistsProductWhenFindByIdThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.findById(id));

        assertThat(badRequestException).hasMessage("Produto não encontrado.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("findById: Esperado que ao receber um produto inexistente, retorne um produto")
    public void givenNotExistsProductWhenFindByIdThenProduct() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(this.createProduct()));

        ProductDto productDto = this.service.findById(id);

        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);

        assertThat(productDto).hasToString("ProductDto({id:1, name:Pizza de Banana})");
    }

    @Test
    @DisplayName("findAll: Esperado que retorne os produtos existentes")
    public void givenProductsWhenFindAllThenProducts() {
        when(this.repository.findAll())
                .thenReturn(List.of(createProduct(1L, "Pizza de Nordestina"), createProduct(2L, "Pizza de Strogonofe de Carne")));

        List<ProductDto> usersDto = this.service.findAll();

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
        assertThat(usersDto).hasToString("[ProductDto({id:1, name:Pizza de Nordestina}), ProductDto({id:2, name:Pizza de Strogonofe de Carne})]");
    }

    @Test
    @DisplayName("delete: Esperado que ao receber um produto inexistente, retorne uma exceção")
    public void givenNotExistsProductWhenDeleteThenException() {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.empty());

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> this.service.delete(id));

        assertThat(badRequestException).hasMessage("Produto não encontrado.");
        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("delete: Esperado que ao receber um produto existente, retorne um produto")
    public void givenExistsProductWhenDeleteThenProduct() {
        Long id = 2L;
        Product product = createProduct(id, "Pastel de Frango");

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(product));

        ProductDto productDto = this.service.delete(id);

        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).delete(eq(product));
        verifyNoMoreInteractions(this.repository);

        assertThat(productDto).hasToString("ProductDto({id:2, name:Pastel de Frango})");
    }

    private boolean checkArgs(Product product) {
        return product.getName().equals(this.productRequest.getName());
    }

    private ProductRequestDto createProductRequestDto() {
        ProductRequestDto product = new ProductRequestDto();

        return product.setName("Pizza de Banana");
    }

    private Product createProduct() {
        return new Product(1L, this.productRequest.getName());
    }

    private Product createProduct(Long id, String name) {
        return new Product(id, name);
    }
}