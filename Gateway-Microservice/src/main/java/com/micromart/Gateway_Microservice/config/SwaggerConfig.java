//package com.micromart.Gateway_Microservice.config;
//
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//
//import org.springdoc.core.properties.SwaggerUiConfigParameters;
//import org.springdoc.core.properties.SwaggerUiConfigProperties;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Objects;
//
//
//@Configuration
//public class SwaggerConfig {
//
//
//    @Bean
//    public CommandLineRunner openApiGroups(
//            RouteDefinitionLocator locator,
//            SwaggerUiConfigParameters swaggerUiConfigParameters) {
//        return args -> Objects.requireNonNull(locator
//                        .getRouteDefinitions().collectList().block())
//                .stream()
//                .map(RouteDefinition::getId)
//                .filter(id -> id.matches(".*-Service"))
//                .map(id -> id.replace("-Service", ""))
//                .forEach(swaggerUiConfigParameters::addGroup);
//    }
//
//
//
//
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI().info(new Info()
//                .title("Review Microservice API").version("1.0"));
//    }
//}
//
//
