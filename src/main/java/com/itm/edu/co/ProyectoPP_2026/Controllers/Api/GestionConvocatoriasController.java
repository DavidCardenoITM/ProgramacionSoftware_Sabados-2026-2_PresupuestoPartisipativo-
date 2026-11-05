package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.ConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.CrearConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.DecisionConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostulacionConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostularConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.ConvocatoriaJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/convocatorias")
@Tag(name = "Convocatorias", description = "Ciclo de vida de convocatorias y postulaciones de beca")
public class GestionConvocatoriasController {

    private final ConvocatoriaJpaServiceInterface service;

    public GestionConvocatoriasController(ConvocatoriaJpaServiceInterface service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una convocatoria", description = "Requiere rol administrador")
    public ConvocatoriaResumen crear(
            @RequestBody(required = false) CrearConvocatoriaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.crear(request, idUsuario);
    }

    @GetMapping
    @Operation(summary = "Listar convocatorias", description = "Permite filtrar por municipio, estado y vigencia")
    public List<ConvocatoriaResumen> listar(
            @Parameter(description = "Id del municipio") @RequestParam(required = false) Integer idMunicipio,
            @Parameter(description = "Estado de la convocatoria") @RequestParam(required = false) String estado,
            @Parameter(description = "Si es true solo devuelve convocatorias abiertas y vigentes")
            @RequestParam(defaultValue = "false") boolean soloVigentes) {
        return service.listar(idMunicipio, estado, soloVigentes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una convocatoria por id")
    public ConvocatoriaResumen obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PatchMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar una convocatoria", description = "Requiere rol administrador")
    public ConvocatoriaResumen cerrar(
            @PathVariable Integer id,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.cerrar(id, idUsuario);
    }

    @PostMapping("/{id}/postulaciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Postular un estudiante a una convocatoria")
    public PostulacionConvocatoriaResumen postular(
            @PathVariable Integer id,
            @RequestBody(required = false) PostularConvocatoriaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.postular(id, request, idUsuario);
    }

    @GetMapping("/{id}/postulaciones")
    @Operation(summary = "Listar postulaciones de una convocatoria", description = "Requiere rol administrador")
    public List<PostulacionConvocatoriaResumen> listarPostulaciones(
            @PathVariable Integer id,
            @RequestParam(required = false) String estado,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.listarPostulaciones(id, estado, idUsuario);
    }

    @PatchMapping("/postulaciones/{idPostulacion}/decision")
    @Operation(summary = "Aprobar o rechazar una postulacion de beca", description = "Requiere rol administrador")
    public PostulacionConvocatoriaResumen decidir(
            @PathVariable Integer idPostulacion,
            @RequestBody(required = false) DecisionConvocatoriaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.decidir(idPostulacion, request, idUsuario);
    }
}
