package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.ActualizarMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.CrearMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.MunicipioResumen;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.MunicipioJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/municipios")
@Tag(name = "Municipios", description = "Gestion del catalogo de municipios")
public class GestionMunicipiosController {

    private final MunicipioJpaServiceInterface service;

    public GestionMunicipiosController(MunicipioJpaServiceInterface service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar municipios")
    public List<MunicipioResumen> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un municipio", description = "Requiere rol administrador")
    public MunicipioResumen crear(
            @RequestBody(required = false) CrearMunicipioRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.crear(request, idUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un municipio", description = "Requiere rol administrador")
    public MunicipioResumen actualizar(
            @PathVariable Integer id,
            @RequestBody(required = false) ActualizarMunicipioRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.actualizar(id, request, idUsuario);
    }
}
