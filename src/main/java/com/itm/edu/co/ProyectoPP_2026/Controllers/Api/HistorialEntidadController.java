package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HistorialDtos.HistorialEstudianteResumen;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.HistorialEntidadJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entidades")
@Tag(name = "Historial de entidades", description = "Consulta de estudiantes que prestaron servicio en una entidad")
public class HistorialEntidadController {

    private final HistorialEntidadJpaServiceInterface service;

    public HistorialEntidadController(HistorialEntidadJpaServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/{idEntidad}/historial")
    @Operation(summary = "Listar el historial de estudiantes de una entidad",
            description = "Requiere ser representante de la entidad o administrador")
    public List<HistorialEstudianteResumen> listar(
            @PathVariable Integer idEntidad,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.listar(idEntidad, idUsuario);
    }
}
