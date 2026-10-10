
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Estudiante-Habilidad",
        description = "Gestiona las relaciones entre estudiantes y sus habilidades."
)
public interface EstudianteHabilidadApi {

    @Operation(
            summary = "Listar relaciones estudiante-habilidad",
            description = "Obtiene todas las relaciones registradas entre estudiantes y habilidades."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relaciones consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las relaciones")
    })
    @GetMapping("/listar")
    ResponseEntity<List<EstudianteHabilidad>> listarEstudianteHabilidad();

    @Operation(
            summary = "Listar habilidades de un estudiante",
            description = "Consulta las habilidades asociadas al identificador del estudiante indicado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades consultadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Identificador del estudiante inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las habilidades")
    })
    @GetMapping("/listar/estudiante/{idEstudiante}")
    ResponseEntity<List<EstudianteHabilidad>> listarPorEstudiante(
            @PathVariable("idEstudiante") Integer idEstudiante
    );

    @Operation(
            summary = "Asociar habilidad a estudiante",
            description = "Registra una relación entre un estudiante y una habilidad usando sus identificadores."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan identificadores obligatorios"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la relación")
    })
    @PostMapping("/insertar")
    ResponseEntity<EstudianteHabilidad> insertarEstudianteHabilidad(
            @RequestBody EstudianteHabilidad eh
    );

    @Operation(
            summary = "Eliminar relación estudiante-habilidad",
            description = "Elimina la relación identificada por el estudiante y la habilidad."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación eliminada correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontró o no se eliminó la relación"),
            @ApiResponse(responseCode = "400", description = "Los identificadores son inválidos")
    })
    @DeleteMapping("/eliminar/{idEstudiante}/{idHabilidad}")
    ResponseEntity<Void> eliminarEstudianteHabilidad(
            @PathVariable("idEstudiante") Integer idEstudiante,
            @PathVariable("idHabilidad") Integer idHabilidad
    );
}
