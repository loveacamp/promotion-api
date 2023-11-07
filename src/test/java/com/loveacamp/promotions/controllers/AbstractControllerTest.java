package com.loveacamp.promotions.controllers;

import com.loveacamp.promotions.PromotionsApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loveacamp.promotions.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PromotionsApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected void responseStatus(MvcResult mvcResult, HttpStatus httpStatus) {
        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(httpStatus.value());
    }

    protected <TYPE> String serializeInput(TYPE value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    protected String getContentAsString(MvcResult mvcResult) throws UnsupportedEncodingException {
        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        if (result.isEmpty()) {
            return "{}";
        }

        return result;
    }
}
