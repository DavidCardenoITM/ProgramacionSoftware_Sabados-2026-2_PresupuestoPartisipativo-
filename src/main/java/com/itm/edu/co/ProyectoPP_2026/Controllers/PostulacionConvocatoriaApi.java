
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Postulaciones a convocatorias",
        description = "Gestiona las postulaciones de estudiantes a convocatorias.")
public interface PostulacionConvocatoriaApi {

    @Operation(summary = "Listar postulaciones",
            description = "Obtiene todas las postulaciones a convocatorias.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postulaciones consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    ResponseEntity<List<PostulacionConvocatoria>> listarPostulacion();

    @Operation(summary = "Consultar postulación por ID",
            description = "Busca una postulación mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postulación encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<PostulacionConvocatoria> listarPostulacionId(
            @PathVariable("id") Integer id
    );

    @Operation(summary = "Crear postulación",
            description = "Registra la postulación de un estudiante a una convocatoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postulación registrada correctamente"),
            @ApiResponse(responseCode = "204", description = "El registro no devolvió contenido"),
            @ApiResponse(responseCode = "400", description = "Faltan identificadores obligatorios"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar la postulación")
    })
    @PostMapping("/insertar")
    ResponseEntity<PostulacionConvocatoria> insertarPostulacion(
            @RequestBody PostulacionConvocatoria postulacion
    );

    @Operation(summary = "Actualizar postulación",
            description = "Actualiza los datos de una postulación existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postulación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Falta el identificador de la postulación"),
            @ApiResponse(responseCode = "500", description = "No fue posible actualizar la postulación")
    })
    @PutMapping("/actualizar")
    ResponseEntity<PostulacionConvocatoria> actualizarPostulacion(
            @RequestBody PostulacionConvocatoria postulacion
    );

    @Operation(summary = "Eliminar postulación",
            description = "Elimina una postulación mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado de la eliminación"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "No fue posible eliminar la postulación")
    })
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarPostulacion(@PathVariable("id") Integer id);
}
