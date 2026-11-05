package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistrarHorasRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistroHorasResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ResumenHoras;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ValidarHorasRequest;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.RegistroHorasJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/horas")
@Tag(name = "Horas", description = "Registro, validacion y resumen de horas de labor social")
public class GestionHorasController {

    private final RegistroHorasJpaServiceInterface service;

    public GestionHorasController(RegistroHorasJpaServiceInterface service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una jornada de horas", description = "Requiere una asignacion activa")
    public RegistroHorasResumen registrar(
            @RequestBody(required = false) RegistrarHorasRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.registrar(request, idUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar una jornada de horas pendiente")
    public RegistroHorasResumen actualizar(
            @PathVariable Long id,
            @RequestBody(required = false) RegistrarHorasRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.actualizar(id, request, idUsuario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una jornada de horas pendiente")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        service.eliminar(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar las jornadas de una postulacion")
    public List<RegistroHorasResumen> listar(
            @RequestParam Long idPostulacionOferta,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.listar(idPostulacionOferta, idUsuario);
    }

    @PatchMapping("/{id}/validacion")
    @Operation(summary = "Validar o rechazar una jornada de horas",
            description = "Requiere ser representante de la entidad o administrador")
    public RegistroHorasResumen validar(
            @PathVariable Long id,
            @RequestBody(required = false) ValidarHorasRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.validar(id, request, idUsuario);
    }

    @GetMapping("/resumen/{idPostulacionOferta}")
    @Operation(summary = "Consultar el resumen de horas de una postulacion")
    public ResumenHoras resumen(
            @PathVariable Long idPostulacionOferta,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.resumen(idPostulacionOferta, idUsuario);
    }
}
