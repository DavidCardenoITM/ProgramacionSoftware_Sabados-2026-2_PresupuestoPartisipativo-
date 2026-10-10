
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Postulaciones a ofertas",
        description = "Gestiona las postulaciones de estudiantes a ofertas de labor social.")
public interface PostulacionOfertaApi {

    @Operation(summary = "Listar postulaciones a ofertas",
            description = "Obtiene todas las postulaciones registradas a las ofertas de labor social.")
    @ApiResponse(responseCode = "200", description = "Postulaciones consultadas correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostulacionOferta.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar postulaciones")
    @GetMapping("/listar")
    ResponseEntity<List<PostulacionOferta>> listarPostulaciones();

    @Operation(summary = "Consultar postulacion por ID",
            description = "Busca una postulacion mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Postulacion encontrada",
            content = @Content(schema = @Schema(implementation = PostulacionOferta.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "204", description = "Postulacion no encontrada")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<PostulacionOferta> buscarPorId(@PathVariable("id") Long id);

    @Operation(summary = "Crear postulacion a oferta",
            description = "Registra una postulacion a una oferta. No permite ofertas cerradas.")
    @ApiResponse(responseCode = "201", description = "Postulacion creada correctamente",
            content = @Content(schema = @Schema(implementation = PostulacionOferta.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos u oferta cerrada")
    @ApiResponse(responseCode = "500", description = "Error al crear")
    @PostMapping("/insertar")
    ResponseEntity<PostulacionOferta> crearPostulacion(@RequestBody PostulacionOferta postulacion);

    @Operation(summary = "Actualizar postulacion",
            description = "Actualiza estado u otros datos de una postulacion existente.")
    @ApiResponse(responseCode = "200", description = "Postulacion actualizada correctamente",
            content = @Content(schema = @Schema(implementation = PostulacionOferta.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<PostulacionOferta> actualizarPostulacion(@RequestBody PostulacionOferta postulacion);

    @Operation(summary = "Eliminar postulacion",
            description = "Elimina una postulacion mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Postulacion eliminada correctamente",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarPostulacion(@PathVariable("id") Long id);
}
