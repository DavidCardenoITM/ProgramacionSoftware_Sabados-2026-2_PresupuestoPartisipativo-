
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UsuariosApi {

    @Operation(
            tags = {"Usuarios"},
            summary = "Lista todos los usuarios",
            description = "Obtiene los usuarios registrados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuarios consultados correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Usuarios.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al consultar los usuarios"
                    )
            }
    )
    @GetMapping("/listar")
    ResponseEntity<List<Usuarios>> listarUsuario();


    @Operation(
            tags = {"Usuarios"},
            summary = "Busca un usuario por su ID",
            description = "Obtiene un usuario según el identificador ingresado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Usuarios.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No se encontró el usuario"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al consultar el usuario"
                    )
            }
    )
    @GetMapping("/listar/{id}")
    ResponseEntity<Usuarios> listarUsuarioId(
            @PathVariable Integer id
    );


    @Operation(
            tags = {"Usuarios"},
            summary = "Registra un usuario",
            description = "Inserta un nuevo usuario en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Usuarios.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al registrar el usuario"
                    )
            }
    )
    @PostMapping("/insertar")
    ResponseEntity<Usuarios> insertarUsuario(
            @RequestBody Usuarios usuario
    );


    @Operation(
            tags = {"Usuarios"},
            summary = "Actualiza un usuario",
            description = "Actualiza los datos de un usuario registrado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Usuarios.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al actualizar el usuario"
                    )
            }
    )
    @PutMapping("/actualizar")
    ResponseEntity<Usuarios> actualizarUsuario(
            @RequestBody Usuarios usuario
    );


    @Operation(
            tags = {"Usuarios"},
            summary = "Corrige el correo de un usuario",
            description = "Reemplaza el correo actual por uno nuevo.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Correo corregido correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Usuarios.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Los correos ingresados no son válidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al corregir el correo"
                    )
            }
    )
    @PatchMapping("/corregircorreo")
    ResponseEntity<Usuarios> corregirCorreo(
            @RequestParam String correo,
            @RequestParam String nuevoCorreo
    );


    @Operation(
            tags = {"Usuarios"},
            summary = "Elimina un usuario",
            description = "Elimina un usuario usando su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operación de eliminación completada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Boolean.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error al eliminar el usuario"
                    )
            }
    )
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarUsuario(
            @PathVariable Integer id
    );
}
