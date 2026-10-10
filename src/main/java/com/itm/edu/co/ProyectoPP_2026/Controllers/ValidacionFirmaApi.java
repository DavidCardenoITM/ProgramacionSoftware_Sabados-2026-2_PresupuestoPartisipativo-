
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Validaciones de firma",
        description = "Gestiona las validaciones de firma de los registros de horas de labor social."
)
public interface ValidacionFirmaApi {

    @Operation(
            summary = "Listar validaciones de firma",
            description = "Obtiene todas las validaciones de firma registradas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validaciones consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar las validaciones")
    })
    @GetMapping("/listar")
    ResponseEntity<List<ValidacionFirma>> listarValidacionFirma();

    @Operation(
            summary = "Consultar validación por ID",
            description = "Busca una validación de firma mediante su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validación consultada correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar la validación")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<ValidacionFirma> listarValidacionFirmaId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Registrar validación de firma",
            description = "Registra la validación de un registro de horas por parte de un representante. El resultado debe ser APROBADO o RECHAZADO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validación registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o el resultado no es válido"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la validación")
    })
    @PostMapping("/insertar")
    ResponseEntity<ValidacionFirma> insertarValidacionFirma(
            @RequestBody ValidacionFirma validacion
    );
}
