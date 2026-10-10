
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Ofertas de labor social",
        description = "Gestiona las ofertas de labor social disponibles.")
public interface OfertaApi {

    @Operation(summary = "Listar ofertas",
            description = "Obtiene todas las ofertas de labor social registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ofertas consultadas correctamente")
    })
    @GetMapping
    List<OfertaLaborSocial> listarOfertas();

    @Operation(summary = "Consultar oferta por ID",
            description = "Busca una oferta de labor social mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta consultada correctamente"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada")
    })
    @GetMapping("/{id}")
    OfertaLaborSocial buscarPorId(@PathVariable("id") Integer id);

    @Operation(summary = "Crear oferta",
            description = "Registra una nueva oferta de labor social.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado de la creación de la oferta")
    })
    @PostMapping
    Boolean insertarOferta(@RequestBody OfertaLaborSocial oferta);

    @Operation(summary = "Actualizar oferta",
            description = "Actualiza los datos de una oferta de labor social.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta actualizada correctamente")
    })
    @PutMapping
    OfertaLaborSocial actualizarOferta(@RequestBody OfertaLaborSocial oferta);

    @Operation(summary = "Eliminar oferta",
            description = "Elimina una oferta mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado de la eliminación de la oferta")
    })
    @DeleteMapping("/{id}")
    Boolean eliminarOferta(@PathVariable("id") Integer id);
}
