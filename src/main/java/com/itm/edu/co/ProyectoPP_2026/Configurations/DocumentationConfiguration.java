package com.itm.edu.co.ProyectoPP_2026.Configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API de Presupuesto Participativo")
                                .version("1.0.0")
                                .description(
                                        "API para la gestión de ofertas, postulaciones y registros de horas de labor social."
                                )
                                .contact(
                                        new Contact()
                                                .name("Equipo de desarrollo")
                                )
                );
    }
}