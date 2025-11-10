package com.example.service_erp.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                // Habilita soporte para UUID (clave primaria de tus entidades)
                .scalar(ExtendedScalars.UUID)
                // Habilita soporte para fechas si lo necesitás más adelante
                .scalar(ExtendedScalars.DateTime)
                // Mapea el escalar Object de ExtendedScalars al nombre Json del esquema
                .scalar(GraphQLScalarType.newScalar()
                        .name("Json")
                        .description("Scalar type for JSON objects")
                        .coercing(ExtendedScalars.Object.getCoercing())
                        .build());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}