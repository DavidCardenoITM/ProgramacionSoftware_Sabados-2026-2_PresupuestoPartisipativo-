
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Convocatorias", description = "Gestión de convocatorias")
public interface ConvocatoriaApi {

    @Operation(summary = "Lista las convocatorias",
            description = "Obtiene todas las convocatorias registradas.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = Convocatoria.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar convocatorias")
    @GetMapping("/listar")
    ResponseEntity<List<Convocatoria>> listarConvocatoria();

    @Operation(summary = "Busca una convocatoria por ID",
            description = "Consulta una convocatoria mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Convocatoria encontrada",
            content = @Content(schema = @Schema(implementation = Convocatoria.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "204", description = "No se encontró la convocatoria")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<Convocatoria> listarConvocatoriaId(@PathVariable Integer id);

    @Operation(summary = "Registra una convocatoria",
            description = "Inserta una nueva convocatoria en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Convocatoria registrada",
            content = @Content(schema = @Schema(implementation = Convocatoria.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al registrar")
    @PostMapping("/insertar")
    ResponseEntity<Convocatoria> insertarConvocatoria(
            @RequestBody Convocatoria convocatoria);

    @Operation(summary = "Actualiza una convocatoria",
            description = "Actualiza los datos de una convocatoria existente.")
    @ApiResponse(responseCode = "200", description = "Convocatoria actualizada",
            content = @Content(schema = @Schema(implementation = Convocatoria.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<Convocatoria> actualizarConvocatoria(
            @RequestBody Convocatoria convocatoria);

    @Operation(summary = "Elimina una convocatoria",
            description = "Elimina una convocatoria mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Operación de eliminación completada",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarConvocatoria(@PathVariable Integer id);
}
