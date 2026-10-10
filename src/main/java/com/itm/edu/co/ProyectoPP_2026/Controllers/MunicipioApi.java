
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Municipios",
        description = "Gestiona los municipios registrados en el sistema."
)
public interface MunicipioApi {

    @Operation(
            summary = "Listar municipios",
            description = "Obtiene todos los municipios registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipios consultados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar los municipios")
    })
    @GetMapping("/listar")
    ResponseEntity<List<Municipio>> listarMunicipio();

    @Operation(
            summary = "Consultar municipio por ID",
            description = "Busca un municipio mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipio consultado correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay contenido para la consulta"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno al consultar el municipio")
    })
    @GetMapping("/listar/{id}")
    ResponseEntity<Municipio> listarMunicipioId(
            @PathVariable("id") Integer id
    );

    @Operation(
            summary = "Registrar municipio",
            description = "Registra un municipio utilizando los datos recibidos, incluido su código DANE."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipio registrado correctamente"),
            @ApiResponse(responseCode = "204", description = "El registro no devolvió contenido"),
            @ApiResponse(responseCode = "400", description = "Falta el código DANE"),
            @ApiResponse(responseCode = "500", description = "No fue posible registrar el municipio")
    })
    @PostMapping("/insertar")
    ResponseEntity<Municipio> insertarMunicipio(
            @RequestBody Municipio municipio
    );

    @Operation(
            summary = "Actualizar municipio",
            description = "Actualiza los datos de un municipio existente mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipio actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan datos o el identificador del municipio"),
            @ApiResponse(responseCode = "500", description = "No fue posible actualizar el municipio")
    })
    @PutMapping("/actualizar")
    ResponseEntity<Municipio> actualizarMunicipio(
            @RequestBody Municipio municipio
    );

    @Operation(
            summary = "Eliminar municipio",
            description = "Elimina un municipio mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación de eliminación completada"),
            @ApiResponse(responseCode = "400", description = "Identificador inválido"),
            @ApiResponse(responseCode = "500", description = "No fue posible eliminar el municipio")
    })
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarMunicipio(
            @PathVariable("id") Integer id
    );
}
