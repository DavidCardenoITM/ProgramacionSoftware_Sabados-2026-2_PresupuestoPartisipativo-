
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Experiencias",
        description = "Gestiona las experiencias registradas en las hojas de vida."
)
public interface ExperienciaApi {

    @Operation(
            summary = "Listar experiencias",
            description = "Obtiene todas las experiencias registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experiencias consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las experiencias")
    })
    @GetMapping("/listar")
    ResponseEntity<List<Experiencia>> listarExperiencia();

    @Operation(
            summary = "Consultar experiencia por ID",
            description = "Busca una experiencia mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experiencia consultada correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar la experiencia")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<Experiencia> listarExperienciaId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Listar experiencias por hoja de vida",
            description = "Obtiene el historial de experiencias asociado a una hoja de vida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial consultado correctamente"),
            @ApiResponse(responseCode = "400", description = "Identificador de hoja de vida inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar el historial")
    })
    @GetMapping("/listar/hoja-de-vida/{idHojaDeVida}")
    ResponseEntity<List<Experiencia>> listarExperienciaPorHojaDeVida(
            @PathVariable("idHojaDeVida") Integer idHojaDeVida
    );

    @Operation(
            summary = "Registrar experiencia",
            description = "Registra una experiencia laboral o académica asociada a una hoja de vida."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experiencia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos obligatorios faltantes o fechas inválidas"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la experiencia")
    })
    @PostMapping("/insertar")
    ResponseEntity<Experiencia> insertarExperiencia(
            @RequestBody Experiencia experiencia
    );
}
