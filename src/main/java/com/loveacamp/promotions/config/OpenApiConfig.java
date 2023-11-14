package com.loveacamp.promotions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(this.getInfo());
    }

    private Info getInfo(){
        return new Info()
                .title("Promotions")
                .description("Gerenciar promoções.")
                .version("0.1");
    }
}
