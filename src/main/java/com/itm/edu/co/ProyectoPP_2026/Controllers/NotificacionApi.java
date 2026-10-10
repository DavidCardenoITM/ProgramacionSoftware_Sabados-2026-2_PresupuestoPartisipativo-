
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Notificaciones", description = "Gestiona las notificaciones de los usuarios.")
public interface NotificacionApi {

    @Operation(summary = "Listar notificaciones",
            description = "Obtiene todas las notificaciones registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificaciones consultadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    ResponseEntity<List<Notificacion>> listarNotificacion();

    @Operation(summary = "Consultar notificación por ID",
            description = "Busca una notificación mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<Notificacion> listarNotificacionId(@PathVariable("id") Integer id);

    @Operation(summary = "Crear notificación",
            description = "Registra una notificación para un usuario con su mensaje.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios"),
            @ApiResponse(responseCode = "500", description = "No fue posible crear la notificación")
    })
    @PostMapping("/insertar")
    ResponseEntity<Notificacion> insertarNotificacion(@RequestBody Notificacion notificacion);

    @Operation(summary = "Actualizar notificación",
            description = "Actualiza los datos de una notificación existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Falta el identificador"),
            @ApiResponse(responseCode = "500", description = "No fue posible actualizar la notificación")
    })
    @PutMapping("/actualizar")
    ResponseEntity<Notificacion> actualizarNotificacion(@RequestBody Notificacion notificacion);

    @Operation(summary = "Marcar notificación como leída o no leída",
            description = "Cambia el estado de lectura de una notificación mediante su ID y el parámetro leido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de lectura actualizado"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "No fue posible cambiar el estado")
    })
    @PatchMapping("/marcarleido/{id}")
    ResponseEntity<Boolean> marcarLeido(
            @PathVariable("id") Integer id,
            @RequestParam("leido") Boolean leido
    );

    @Operation(summary = "Eliminar notificación",
            description = "Elimina una notificación mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "No fue posible eliminar la notificación")
    })
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarNotificacion(@PathVariable("id") Integer id);
}
