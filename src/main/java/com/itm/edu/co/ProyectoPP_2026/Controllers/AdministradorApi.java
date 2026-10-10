
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Administradores", description = "Gestión de administradores")
public interface AdministradorApi {

    @Operation(summary = "Lista los administradores",
            description = "Obtiene todos los administradores registrados.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = Administrador.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar administradores")
    @GetMapping("/listar")
    ResponseEntity<List<Administrador>> listarAdministrador();

    @Operation(summary = "Busca un administrador por ID",
            description = "Consulta un administrador mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Administrador encontrado",
            content = @Content(schema = @Schema(implementation = Administrador.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "204", description = "No se encontró el administrador")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<Administrador> listarAdministradorId(@PathVariable Integer id);

    @Operation(summary = "Registra un administrador",
            description = "Inserta un administrador en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Administrador registrado",
            content = @Content(schema = @Schema(implementation = Administrador.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al registrar")
    @PostMapping("/insertar")
    ResponseEntity<Administrador> insertarAdministrador(
            @RequestBody Administrador administrador);

    @Operation(summary = "Actualiza un administrador",
            description = "Actualiza los datos de un administrador existente.")
    @ApiResponse(responseCode = "200", description = "Administrador actualizado",
            content = @Content(schema = @Schema(implementation = Administrador.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<Administrador> actualizarAdministrador(
            @RequestBody Administrador administrador);

    @Operation(summary = "Elimina un administrador",
            description = "Elimina un administrador mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Operación de eliminación completada",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador inválido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarAdministrador(@PathVariable Integer id);
}
