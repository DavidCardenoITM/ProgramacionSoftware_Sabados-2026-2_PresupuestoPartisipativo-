
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Postulaciones a ofertas",
        description = "Gestiona las postulaciones de estudiantes a ofertas de labor social.")
public interface PostulacionOfertaApi {

    @Operation(summary = "Listar postulaciones a ofertas",
            description = "Obtiene todas las postulaciones registradas a las ofertas de labor social.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Postulaciones consultadas correctamente")
    })
    @GetMapping
    List<PostulacionOferta> listarPostulaciones();

    @Operation(summary = "Crear postulación a oferta",
            description = "Registra una postulación a una oferta y devuelve un mensaje con el resultado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado del registro de la postulación")
    })
    @PostMapping
    String crearPostulacion(@RequestBody PostulacionOferta postulacion);
}
