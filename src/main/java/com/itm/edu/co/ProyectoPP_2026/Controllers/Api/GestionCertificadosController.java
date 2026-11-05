package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.CertificadoResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.GenerarCertificadoRequest;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.CertificadoJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/certificados")
@Tag(name = "Certificados", description = "Generacion y verificacion de certificados de labor social")
public class GestionCertificadosController {

    private final CertificadoJpaServiceInterface service;

    public GestionCertificadosController(CertificadoJpaServiceInterface service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generar el certificado de un estudiante",
            description = "Requiere haber completado las horas validadas de la oferta")
    public CertificadoResumen generar(
            @RequestBody(required = false) GenerarCertificadoRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.generar(request, idUsuario);
    }

    @GetMapping("/verificar/{codigo}")
    @Operation(summary = "Verificar un certificado por su codigo")
    public CertificadoResumen verificar(@PathVariable String codigo) {
        return service.consultar(codigo);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    @Operation(summary = "Consultar el certificado de un estudiante")
    public CertificadoResumen obtenerPorEstudiante(
            @PathVariable Long idEstudiante,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.obtenerPorEstudiante(idEstudiante, idUsuario);
    }
}
