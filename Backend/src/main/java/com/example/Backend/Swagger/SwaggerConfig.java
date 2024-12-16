package com.example.Backend.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info().title("Api REST con MYSQL y SPRING BOOT").version("1.0 SNAPSHOT")
            .contact(new Contact().name("Juan Sebastian Zambrano").email("jszambranop@gmail.com")));
    }
}