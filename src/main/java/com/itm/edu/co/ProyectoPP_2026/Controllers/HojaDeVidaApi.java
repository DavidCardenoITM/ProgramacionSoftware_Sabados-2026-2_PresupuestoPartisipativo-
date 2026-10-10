
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Hojas de vida",
        description = "Gestiona las hojas de vida de los estudiantes."
)
public interface HojaDeVidaApi {

    @Operation(
            summary = "Listar hojas de vida",
            description = "Obtiene todas las hojas de vida registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hojas de vida consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las hojas de vida")
    })
    @GetMapping("/listar")
    ResponseEntity<List<HojaDeVida>> listarHojaDeVida();

    @Operation(
            summary = "Consultar hoja de vida por ID",
            description = "Busca una hoja de vida mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hoja de vida consultada correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar la hoja de vida")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<HojaDeVida> listarHojaDeVidaId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Consultar hoja de vida por estudiante",
            description = "Busca la hoja de vida asociada al identificador de un estudiante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hoja de vida consultada correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador de estudiante inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar la hoja de vida")
    })
    @GetMapping("/listar/estudiante/{idEstudiante}")
    ResponseEntity<HojaDeVida> listarHojaDeVidaPorEstudiante(
            @PathVariable("idEstudiante") Integer idEstudiante
    );

    @Operation(
            summary = "Registrar hoja de vida",
            description = "Crea una hoja de vida asociada a un estudiante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hoja de vida registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Falta el identificador del estudiante"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la hoja de vida")
    })
    @PostMapping("/insertar")
    ResponseEntity<HojaDeVida> insertarHojaDeVida(
            @RequestBody HojaDeVida hoja
    );
}
