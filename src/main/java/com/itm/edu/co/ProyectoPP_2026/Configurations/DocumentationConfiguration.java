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
                                .title("API de Labor Social")
                                .version("2.0.0")
                                .description(
                                        "API para la gestion de cuentas, municipios, convocatorias, ofertas, "
                                                + "postulaciones, horas de labor social y certificados. "
                                                + "Los endpoints de la version /api/v1 reciben el usuario que ejecuta "
                                                + "la accion en el encabezado X-Usuario-Id."
                                )
                                .contact(
                                        new Contact()
                                                .name("Equipo de desarrollo")
                                )
                );
    }
}
