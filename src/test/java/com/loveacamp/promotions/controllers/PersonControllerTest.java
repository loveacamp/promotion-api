package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.dto.requests.PersonRequestDto;
import com.loveacamp.promotions.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest extends  AbstractControllerTest{

    @Mock
    private UserRepository repository;

    private PersonRequestDto personRequest;

    @BeforeEach
    public void setup() {
        this.personRequest = createPersonRequestDto();
    }

    @Test
    @DisplayName("save: esperado que salve uma pessoa.")
    public void givenPersonWhenSaveThenExpects200() throws Exception {
        mockMvc.perform(post("/api/people")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeInput(this.personRequest))
        ).andDo(result -> {
            responseStatus(result, HttpStatus.OK);
//            JSONAssert.assertEquals(
//                    """
//                            null
//                            """, getContentAsString(result), true
//            );
        });
    }

    private PersonRequestDto createPersonRequestDto() {
        return new PersonRequestDto("John Doe", "john@email.com");
    }
}