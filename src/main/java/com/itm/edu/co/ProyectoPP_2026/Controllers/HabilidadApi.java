
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Habilidades",
        description = "Gestiona las habilidades disponibles en el sistema."
)
public interface HabilidadApi {

    @Operation(
            summary = "Listar habilidades",
            description = "Obtiene todas las habilidades registradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las habilidades")
    })
    @GetMapping("/listar")
    ResponseEntity<List<Habilidad>> listarHabilidad();

    @Operation(
            summary = "Consultar habilidad por ID",
            description = "Busca una habilidad mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidad consultada correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar la habilidad")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<Habilidad> listarHabilidadId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Registrar habilidad",
            description = "Registra una habilidad con su nombre y categoría."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidad registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la habilidad")
    })
    @PostMapping("/insertar")
    ResponseEntity<Habilidad> insertarHabilidad(
            @RequestBody Habilidad habilidad
    );
}
