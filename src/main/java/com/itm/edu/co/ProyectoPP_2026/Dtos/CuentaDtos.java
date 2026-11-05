package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class CuentaDtos {

    private CuentaDtos() {
    }

    public record CuentaResumen(
            Integer id,
            String nombres,
            String apellidos,
            String correo,
            String rol,
            String estado,
            LocalDate fechaRegistro) {
    }

    public record CambiarEstadoCuentaRequest(String estado) {
    }

    public record CambiarRolCuentaRequest(String rol) {
    }
}
