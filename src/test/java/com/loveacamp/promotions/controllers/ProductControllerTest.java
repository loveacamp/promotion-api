package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.requests.ProductRequestDto;
import com.loveacamp.promotions.entities.Product;
import com.loveacamp.promotions.repositories.ProductRepository;
import com.loveacamp.promotions.utils.LessAndMoreCharacters;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class ProductControllerTest extends AbstractControllerTest {
    @MockBean
    private ProductRepository repository;

    private ProductRequestDto productRequest;

    private final String less = LessAndMoreCharacters.less3Characters;

    private final String more = LessAndMoreCharacters.more255Characters;

    @BeforeEach
    public void setup() {
        this.productRequest = this.createProductRequestDto();
    }

    @Test
    @DisplayName("POST /api/products: Esperado que ao receber um dto inválido, com nome nulo, retorne uma exceção")
    public void givenProductsWhenSaveWithNameNullThenExpects400() throws Exception {
        productRequest.setName(null);

        mockMvc.perform(post("/api/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/products: Esperado que ao receber um dto inválido, com nome menor que 3 caracteres, retorne uma exceção")
    public void givenProductsWhenSaveWithNameWithLessThan3CharactersThenExpects400() throws Exception {
        productRequest.setName(this.less);

        mockMvc.perform(post("/api/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/products: Esperado que ao receber um dto inválido, com nome maior que 255 caracteres, retorne uma exceção")
    public void givenProductsWhenSaveWithNameWithMoreThan255CharactersThenExpects400() throws Exception {
        productRequest.setName(this.more);

        mockMvc.perform(post("/api/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("POST /api/products: Esperado que ao receber um dto válido, retorne um produto")
    public void givenProductsWhenSaveThenExpects200() throws Exception {
        when(this.repository.findByName(eq(this.productRequest.getName())))
                .thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createProduct());

        mockMvc.perform(post("/api/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                        {
                                            "id":1,
                                            "name":"Pizza de Banana"
                                        }
                                    """,
                            getContentAsString(result), true);
                });

        verify(this.repository, times(1)).findByName(eq(this.productRequest.getName()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("PUT /api/products/{id}: Esperado que ao receber um dto inválido, com nome nulo, retorne uma exceção")
    public void givenProductsWhenUpdateWithNameNullThenExpects400() throws Exception {
        productRequest.setName(null);

        mockMvc.perform(put("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/products/{id}: Esperado que ao receber um dto inválido, com nome menor que 3 caracteres, retorne uma exceção")
    public void givenProductsWhenUpdateWithNameWithLessThan3CharactersThenExpects400() throws Exception {
        productRequest.setName(this.less);

        mockMvc.perform(put("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/products/{id}: Esperado que ao receber um dto inválido, com nome maior que 255 caracteres, retorne uma exceção")
    public void givenProductsWhenUpdateWithNameWithMoreThan255CharactersThenExpects400() throws Exception {
        productRequest.setName(this.more);

        mockMvc.perform(put("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "field": "name",
                                                "message": "tamanho deve ser entre 3 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), false);
                });
    }

    @Test
    @DisplayName("PUT /api/products/{id}: Esperado que ao receber um dto válido, retorne um produto")
    public void givenProductsWhenUpdateThenExpects200() throws Exception {
        Long id = 1L;

        when(this.repository.findByIdAndName(eq(id), eq(this.productRequest.getName())))
                .thenReturn(Optional.empty());
        when(this.repository.save(argThat(this::checkArgs))).thenReturn(this.createProduct());

        mockMvc.perform(put(String.format("/api/products/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(productRequest)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id":1,
                                        "name":"Pizza de Banana"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findByIdAndName(eq(id), eq(this.productRequest.getName()));
        verify(this.repository, times(1)).save(argThat(this::checkArgs));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("GET /api/products/{id}: Esperado que ao receber um id válido, retorne um produto")
    public void givenProductsWhenFindByIdThenExpects200() throws Exception {
        Long id = 1L;

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(this.createProduct()));

        mockMvc.perform(get(String.format("/api/products/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id":1,
                                        "name":"Pizza de Banana"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findById(eq(id));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("GET /api/products: Esperado que ao receber a chamada, retorne uma lista de produtos")
    public void givenProductsWhenFindAllThenExpects200() throws Exception {
        when(this.repository.findAll())
                .thenReturn(List.of(createProduct(1L, "Pizza de Nordestina"), createProduct(2L, "Pizza de Strogonofe de Carne")));

        mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    [
                                        {
                                            'id':1,
                                            name:'Pizza de Nordestina'
                                        },
                                        {
                                            'id':2,
                                            'name':'Pizza de Strogonofe de Carne'
                                        }
                                    ]
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("DELETE /api/products/{id}: Esperado que ao receber um id válido, retorne um produto")
    public void givenProductsWhenDeleteThenExpects200() throws Exception {
        Long id = 1L;
        Product product = createProduct(id, "Pastel de Frango");

        when(this.repository.findById(eq(id)))
                .thenReturn(Optional.of(product));

        mockMvc.perform(delete(String.format("/api/products/%s", id))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                                    {
                                        "id":1,
                                        "name":"Pastel de Frango"
                                    }
                                    """,
                            getContentAsString(result), false);
                });

        verify(this.repository, times(1)).findById(eq(id));
        verify(this.repository, times(1)).delete(eq(product));
        verifyNoMoreInteractions(this.repository);
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