
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Representantes de entidades",
        description = "Gestiona los representantes asociados a las entidades receptoras."
)
public interface RepresentanteEntidadApi {

    @Operation(
            summary = "Listar representantes",
            description = "Obtiene la lista de representantes registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representantes consultados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar los representantes")
    })
    @GetMapping("/listar")
    ResponseEntity<List<RepresentanteEntidad>> listarRepresentante();

    @Operation(
            summary = "Consultar representante por ID",
            description = "Busca un representante mediante su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante consultado correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar el representante")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<RepresentanteEntidad> listarRepresentanteId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Registrar representante",
            description = "Registra un representante vinculándolo con un usuario y una entidad receptora."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante registrado correctamente"),
            @ApiResponse(responseCode = "204", description = "El registro no devolvió contenido"),
            @ApiResponse(responseCode = "400", description = "Faltan los identificadores del usuario o de la entidad"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar el representante")
    })
    @PostMapping("/insertar")
    ResponseEntity<RepresentanteEntidad> insertarRepresentante(
            @RequestBody RepresentanteEntidad representante
    );

    @Operation(
            summary = "Actualizar representante",
            description = "Actualiza los datos de un representante existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Representante actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Falta el identificador del representante"),
            @ApiResponse(responseCode = "500", description = "No fue posible actualizar el representante")
    })
    @PutMapping("/actualizar")
    ResponseEntity<RepresentanteEntidad> actualizarRepresentante(
            @RequestBody RepresentanteEntidad representante
    );

    @Operation(
            summary = "Eliminar representante",
            description = "Elimina un representante mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación de eliminación completada"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "No fue posible eliminar el representante")
    })
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarRepresentante(
            @PathVariable("id") Integer id
    );
}
