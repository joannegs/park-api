package com.spring.demoparkapi.config;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    private SecurityScheme securityScheme() {
        return new io.swagger.v3.oas.models.security.SecurityScheme().description("Enter a Bearer token to proceed")
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(new Info()
                .title("REST API - Spring Park")
                .description("API para gestão de estacionamento de veículos")
                .version("v1")
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(new Contact().name("Joanne Silva").email("joanneegabriela@gmail.com")));
    }
}
