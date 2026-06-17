package com.sicredi.credito.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("API de Crédito Sicredi")
                        .version("1.0")
                        .description("API para contratação e consulta de operações de crédito")
                        .contact(new Contact()
                                .name("Veronica Silva")));
    }
}