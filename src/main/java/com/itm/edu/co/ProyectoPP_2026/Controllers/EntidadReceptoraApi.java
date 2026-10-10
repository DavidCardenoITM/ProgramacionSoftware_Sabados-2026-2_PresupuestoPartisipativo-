
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Entidades receptoras",
        description = "Gestión de entidades receptoras de labor social")
public interface EntidadReceptoraApi {

    @Operation(summary = "Lista las entidades receptoras",
            description = "Obtiene todas las entidades receptoras registradas.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = EntidadReceptora.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar entidades")
    @GetMapping("/listar")
    ResponseEntity<List<EntidadReceptora>> listarEntidad();

    @Operation(summary = "Busca una entidad receptora por ID",
            description = "Consulta una entidad mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Entidad encontrada",
            content = @Content(schema = @Schema(implementation = EntidadReceptora.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "204", description = "No se encontró la entidad")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<EntidadReceptora> listarEntidadId(@PathVariable Integer id);

    @Operation(summary = "Registra una entidad receptora",
            description = "Inserta una nueva entidad receptora.")
    @ApiResponse(responseCode = "200", description = "Entidad registrada",
            content = @Content(schema = @Schema(implementation = EntidadReceptora.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al registrar")
    @PostMapping("/insertar")
    ResponseEntity<EntidadReceptora> insertarEntidad(
            @RequestBody EntidadReceptora entidad);

    @Operation(summary = "Actualiza una entidad receptora",
            description = "Actualiza los datos de una entidad existente.")
    @ApiResponse(responseCode = "200", description = "Entidad actualizada",
            content = @Content(schema = @Schema(implementation = EntidadReceptora.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<EntidadReceptora> actualizarEntidad(
            @RequestBody EntidadReceptora entidad);

    @Operation(summary = "Elimina una entidad receptora",
            description = "Elimina una entidad mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Operación de eliminación completada",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarEntidad(@PathVariable Integer id);
}
