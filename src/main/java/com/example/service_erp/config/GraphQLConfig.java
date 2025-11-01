package com.example.service_erp.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                // Habilita soporte para UUID (clave primaria de tus entidades)
                .scalar(ExtendedScalars.UUID)
                // Habilita soporte para fechas si lo necesitás más adelante
                .scalar(ExtendedScalars.DateTime)
                // Habilita soporte para JSON si alguna entidad tiene campos dinámicos
                .scalar(ExtendedScalars.Json);
    }
}