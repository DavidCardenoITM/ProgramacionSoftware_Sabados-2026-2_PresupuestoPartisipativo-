package com.itm.edu.co.ProyectoPP_2026.Controllers.Api;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CuentaDtos.CambiarEstadoCuentaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.CuentaDtos.CambiarRolCuentaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.CuentaDtos.CuentaResumen;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.CuentaJpaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuentas")
@Tag(name = "Cuentas", description = "Gestion administrativa de cuentas de usuario")
public class GestionCuentasController {

    private final CuentaJpaServiceInterface service;

    public GestionCuentasController(CuentaJpaServiceInterface service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar cuentas", description = "Permite filtrar por rol, estado y texto")
    public List<CuentaResumen> listar(
            @Parameter(description = "Rol de la cuenta") @RequestParam(required = false) String rol,
            @Parameter(description = "Estado de la cuenta") @RequestParam(required = false) String estado,
            @Parameter(description = "Texto a buscar en nombres, apellidos o correo") @RequestParam(required = false) String texto) {
        return service.listar(rol, estado, texto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una cuenta por id")
    public CuentaResumen obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar el estado de una cuenta", description = "Requiere rol administrador")
    public CuentaResumen cambiarEstado(
            @PathVariable Integer id,
            @RequestBody(required = false) CambiarEstadoCuentaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.cambiarEstado(id, request == null ? null : request.estado(), idUsuario);
    }

    @PatchMapping("/{id}/rol")
    @Operation(summary = "Cambiar el rol de una cuenta", description = "Requiere rol administrador")
    public CuentaResumen cambiarRol(
            @PathVariable Integer id,
            @RequestBody(required = false) CambiarRolCuentaRequest request,
            @RequestHeader(value = "X-Usuario-Id", defaultValue = "1") Integer idUsuario) {
        return service.cambiarRol(id, request == null ? null : request.rol(), idUsuario);
    }
}
