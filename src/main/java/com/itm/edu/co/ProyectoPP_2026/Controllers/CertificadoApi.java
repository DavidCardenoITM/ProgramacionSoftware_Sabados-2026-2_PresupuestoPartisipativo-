
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Certificados", description = "Consulta y creación de certificados")
public interface CertificadoApi {

    @Operation(summary = "Lista los certificados",
            description = "Obtiene todos los certificados registrados.")
    @ApiResponse(responseCode = "200", description = "Lista consultada correctamente",
            content = @Content(array = @ArraySchema(
                    schema = @Schema(implementation = Certificado.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar certificados")
    @GetMapping
    List<Certificado> listarCertificados();

    @Operation(summary = "Crea un certificado",
            description = "Intenta guardar un nuevo certificado.")
    @ApiResponse(responseCode = "200", description = "Resultado de la operación",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error al crear el certificado")
    @PostMapping
    String crearCertificado(@RequestBody Certificado certificado);
}
