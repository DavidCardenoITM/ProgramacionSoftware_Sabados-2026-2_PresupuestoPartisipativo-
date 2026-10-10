
package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registros de horas",
        description = "Gestiona los registros de horas de labor social.")
public interface RegistroHorasApi {

    @Operation(summary = "Listar registros de horas",
            description = "Obtiene todos los registros de horas de labor social almacenados.")
    @ApiResponse(responseCode = "200", description = "Registros consultados correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegistroHoras.class))))
    @ApiResponse(responseCode = "500", description = "Error al consultar registros")
    @GetMapping("/listar")
    ResponseEntity<List<RegistroHoras>> listarRegistros();

    @Operation(summary = "Consultar registro por ID",
            description = "Busca un registro de horas mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = RegistroHoras.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "204", description = "Registro no encontrado")
    @ApiResponse(responseCode = "500", description = "Error al consultar")
    @GetMapping("/listar/{id}")
    ResponseEntity<RegistroHoras> buscarPorId(@PathVariable("id") Long id);

    @Operation(summary = "Crear registro de horas",
            description = "Registra las horas de labor social. Valida que horasTrabajadas coincida con horaInicio/horaFin.")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente",
            content = @Content(schema = @Schema(implementation = RegistroHoras.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos u horas incoherentes")
    @ApiResponse(responseCode = "500", description = "Error al crear")
    @PostMapping("/insertar")
    ResponseEntity<RegistroHoras> crearRegistro(@RequestBody RegistroHoras registro);

    @Operation(summary = "Actualizar registro de horas",
            description = "Actualiza un registro existente con las mismas validaciones de coherencia.")
    @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente",
            content = @Content(schema = @Schema(implementation = RegistroHoras.class)))
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @ApiResponse(responseCode = "500", description = "Error al actualizar")
    @PutMapping("/actualizar")
    ResponseEntity<RegistroHoras> actualizarRegistro(@RequestBody RegistroHoras registro);

    @Operation(summary = "Eliminar registro de horas",
            description = "Elimina un registro mediante su identificador.")
    @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "Identificador invalido")
    @ApiResponse(responseCode = "500", description = "Error al eliminar")
    @DeleteMapping("/eliminar/{id}")
    ResponseEntity<Boolean> eliminarRegistro(@PathVariable("id") Long id);
}
