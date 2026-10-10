
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Estudiantes", description = "Gestión de estudiantes")
public interface EstudianteApi {

    @Operation(summary = "Lista los estudiantes",
            description = "Obtiene todos los estudiantes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = Estudiante.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar estudiantes")
    @GetMapping("/listar")
    ResponseEntity<List<Estudiante>> listarEstudiante();

    @Operation(summary = "Busca un estudiante por ID",
            description = "Consulta un estudiante mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Estudiante encontrado",
            content = @Content(schema = @Schema(implementation = Estudiante.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "204", description = "No se encontró el estudiante")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<Estudiante> listarEstudianteId(@PathVariable Integer id);

    @Operation(summary = "Registra un estudiante",
            description = "Inserta un nuevo estudiante en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Estudiante registrado",
            content = @Content(schema = @Schema(implementation = Estudiante.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al registrar")
    @PostMapping("/insertar")
    ResponseEntity<Estudiante> insertarEstudiante(
            @RequestBody Estudiante estudiante);

    @Operation(summary = "Actualiza un estudiante",
            description = "Actualiza los datos de un estudiante existente.")
    @ApiResponse(responseCode = "200", description = "Estudiante actualizado",
            content = @Content(schema = @Schema(implementation = Estudiante.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<Estudiante> actualizarEstudiante(
            @RequestBody Estudiante estudiante);

    @Operation(summary = "Elimina un estudiante",
            description = "Elimina un estudiante mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Operación de eliminación completada",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarEstudiante(@PathVariable Integer id);
}
