
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Registros de horas",
        description = "Gestiona los registros de horas de labor social.")
public interface RegistroHorasApi {

    @Operation(summary = "Listar registros de horas",
            description = "Obtiene todos los registros de horas de labor social almacenados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros consultados correctamente")
    })
    @GetMapping
    List<RegistroHoras> listarRegistros();

    @Operation(summary = "Crear registro de horas",
            description = "Registra las horas de labor social y devuelve un mensaje con el resultado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado del registro de horas")
    })
    @PostMapping
    String crearRegistro(@RequestBody RegistroHoras registro);
}
