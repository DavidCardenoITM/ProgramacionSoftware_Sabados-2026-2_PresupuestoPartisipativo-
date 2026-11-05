package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.ActualizarOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.CrearOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.DecisionOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.OfertaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostulacionOfertaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostularOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.OfertaJpaServiceInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.PostulacionOfertaJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/ofertas")
@Tag(name = "Ofertas", description = "Ofertas de labor social y postulaciones de estudiantes")
public class GestionOfertasController {

    private final OfertaJpaServiceInterface ofertaService;
    private final PostulacionOfertaJpaServiceInterface postulacionService;

    public GestionOfertasController(OfertaJpaServiceInterface ofertaService,
                                    PostulacionOfertaJpaServiceInterface postulacionService) {
        this.ofertaService = ofertaService;
        this.postulacionService = postulacionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una oferta", description = "Requiere ser representante de la entidad o administrador")
    public OfertaResumen crear(
            @RequestBody(required = false) CrearOfertaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return ofertaService.crear(request, idUsuario);
    }

    @GetMapping
    @Operation(summary = "Buscar ofertas", description = "Por defecto devuelve solo ofertas activas")
    public List<OfertaResumen> buscar(
            @Parameter(description = "Id de la entidad receptora") @RequestParam(required = false) Integer idEntidad,
            @Parameter(description = "Id del municipio de la entidad") @RequestParam(required = false) Integer idMunicipio,
            @Parameter(description = "Estado de la oferta o TODAS para no filtrar") @RequestParam(required = false) String estado,
            @Parameter(description = "Texto a buscar en titulo o descripcion") @RequestParam(required = false) String texto) {
        return ofertaService.buscar(idEntidad, idMunicipio, estado, texto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una oferta por id")
    public OfertaResumen obtener(@PathVariable Integer id) {
        return ofertaService.obtener(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una oferta en borrador")
    public OfertaResumen actualizar(
            @PathVariable Integer id,
            @RequestBody(required = false) ActualizarOfertaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return ofertaService.actualizar(id, request, idUsuario);
    }

    @PatchMapping("/{id}/publicar")
    @Operation(summary = "Publicar una oferta en borrador")
    public OfertaResumen publicar(
            @PathVariable Integer id,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return ofertaService.publicar(id, idUsuario);
    }

    @PatchMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar una oferta")
    public OfertaResumen cerrar(
            @PathVariable Integer id,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return ofertaService.cerrar(id, idUsuario);
    }

    @PostMapping("/{id}/postulaciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Postular un estudiante a una oferta", description = "Requiere una beca aprobada")
    public PostulacionOfertaResumen postular(
            @PathVariable Long id,
            @RequestBody(required = false) PostularOfertaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.postular(id, request, idUsuario);
    }

    @GetMapping("/{id}/postulaciones")
    @Operation(summary = "Listar postulaciones de una oferta")
    public List<PostulacionOfertaResumen> listarPostulaciones(
            @PathVariable Long id,
            @RequestParam(required = false) String estado,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.listarPorOferta(id, estado, idUsuario);
    }

    @GetMapping("/estudiantes/{idEstudiante}/postulaciones")
    @Operation(summary = "Listar postulaciones de un estudiante")
    public List<PostulacionOfertaResumen> listarPostulacionesEstudiante(
            @PathVariable Long idEstudiante,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.listarPorEstudiante(idEstudiante, idUsuario);
    }

    @PatchMapping("/postulaciones/{idPostulacion}/retirar")
    @Operation(summary = "Retirar una postulacion a oferta")
    public PostulacionOfertaResumen retirar(
            @PathVariable Long idPostulacion,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.retirar(idPostulacion, idUsuario);
    }

    @PatchMapping("/postulaciones/{idPostulacion}/decision")
    @Operation(summary = "Aceptar o rechazar una postulacion a oferta")
    public PostulacionOfertaResumen decidir(
            @PathVariable Long idPostulacion,
            @RequestBody(required = false) DecisionOfertaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.decidir(idPostulacion, request, idUsuario);
    }

    @PatchMapping("/postulaciones/{idPostulacion}/asignar")
    @Operation(summary = "Confirmar la asignacion de un estudiante", description = "Requiere rol administrador")
    public PostulacionOfertaResumen asignar(
            @PathVariable Long idPostulacion,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return postulacionService.asignar(idPostulacion, idUsuario);
    }
}
