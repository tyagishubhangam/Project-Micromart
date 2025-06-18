package com.micromart.OrderMicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Order-Microservice-API")
                .packagesToScan("com.micromart.OrderMicroservice.controllers")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderMicroservice API Documentatiton")
                        .version("1.0")
                        .description("OrderMicroservice API Documentatiton"));
    }
}
