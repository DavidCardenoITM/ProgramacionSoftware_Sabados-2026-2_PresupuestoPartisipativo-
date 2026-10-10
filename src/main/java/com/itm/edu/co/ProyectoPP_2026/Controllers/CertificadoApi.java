
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Certificados", description = "Consulta y creacion de certificados")
public interface CertificadoApi {

    @Operation(summary = "Lista los certificados",
            description = "Obtiene todos los certificados registrados.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Certificado.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar certificados")
    @GetMapping("/listar")
    ResponseEntity<List<Certificado>> listarCertificados();

    @Operation(summary = "Busca un certificado por ID",
            description = "Consulta un certificado mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Certificado encontrado",
            content = @Content(schema = @Schema(implementation = Certificado.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "204", description = "Certificado no encontrado")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<Certificado> buscarPorId(@PathVariable("id") Long id);

    @Operation(summary = "Crea un certificado",
            description = "Guarda un nuevo certificado con codigo de verificacion unico.")
    @ApiResponse(responseCode = "201", description = "Certificado creado correctamente",
            content = @Content(schema = @Schema(implementation = Certificado.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos o codigo duplicado")
    @ApiResponse(responseCode = "500", description = "Error al crear el certificado")
    @PostMapping("/insertar")
    ResponseEntity<Certificado> crearCertificado(@RequestBody Certificado certificado);

    @Operation(summary = "Actualiza un certificado",
            description = "Actualiza los datos de un certificado existente.")
    @ApiResponse(responseCode = "200", description = "Certificado actualizado correctamente",
            content = @Content(schema = @Schema(implementation = Certificado.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<Certificado> actualizarCertificado(@RequestBody Certificado certificado);

    @Operation(summary = "Elimina un certificado",
            description = "Elimina un certificado mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Certificado eliminado correctamente",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarCertificado(@PathVariable("id") Long id);
}
