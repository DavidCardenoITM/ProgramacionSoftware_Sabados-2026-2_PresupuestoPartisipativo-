
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auditorías", description = "Consulta y registro de auditorías")
public interface AuditoriaApi {

    @Operation(summary = "Lista las auditorías",
            description = "Obtiene las auditorías registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = Auditoria.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar auditorías")
    @GetMapping("/listar")
    ResponseEntity<List<Auditoria>> listarAuditoria();

    @Operation(summary = "Busca una auditoría por ID",
            description = "Consulta una auditoría mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Auditoría encontrada",
            content = @Content(schema = @Schema(implementation = Auditoria.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "204", description = "No se encontró la auditoría")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<Auditoria> listarAuditoriaId(@PathVariable Integer id);

    @Operation(summary = "Registra una auditoría",
            description = "Inserta un registro de auditoría en el sistema.")
    @ApiResponse(responseCode = "200", description = "Auditoría registrada",
            content = @Content(schema = @Schema(implementation = Auditoria.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al registrar")
    @PostMapping("/insertar")
    ResponseEntity<Auditoria> insertarAuditoria(@RequestBody Auditoria auditoria);
}
