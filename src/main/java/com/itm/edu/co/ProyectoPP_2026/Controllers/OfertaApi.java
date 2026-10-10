
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ofertas de labor social",
        description = "Gestiona las ofertas de labor social disponibles.")
public interface OfertaApi {

    @Operation(summary = "Listar ofertas",
            description = "Obtiene todas las ofertas de labor social registradas.")
    @ApiResponse(responseCode = "200", description = "Ofertas consultadas correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OfertaLaborSocial.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar ofertas")
    @GetMapping("/listar")
    ResponseEntity<List<OfertaLaborSocial>> listarOfertas();

    @Operation(summary = "Consultar oferta por ID",
            description = "Busca una oferta de labor social mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Oferta consultada correctamente",
            content = @Content(schema = @Schema(implementation = OfertaLaborSocial.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "204", description = "Oferta no encontrada")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<OfertaLaborSocial> buscarPorId(@PathVariable("id") Integer id);

    @Operation(summary = "Crear oferta",
            description = "Registra una nueva oferta de labor social.")
    @ApiResponse(responseCode = "201", description = "Oferta creada correctamente",
            content = @Content(schema = @Schema(implementation = OfertaLaborSocial.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "500", description = "Error al crear")
    @PostMapping("/insertar")
    ResponseEntity<OfertaLaborSocial> insertarOferta(@RequestBody OfertaLaborSocial oferta);

    @Operation(summary = "Actualizar oferta",
            description = "Actualiza los datos de una oferta de labor social.")
    @ApiResponse(responseCode = "200", description = "Oferta actualizada correctamente",
            content = @Content(schema = @Schema(implementation = OfertaLaborSocial.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<OfertaLaborSocial> actualizarOferta(@RequestBody OfertaLaborSocial oferta);

    @Operation(summary = "Eliminar oferta",
            description = "Elimina una oferta mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Oferta eliminada correctamente",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarOferta(@PathVariable("id") Integer id);
}
