package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class CertificadoDtos {

    private CertificadoDtos() {
    }

    public record CertificadoResumen(
            Long id,
            Long idEstudiante,
            LocalDate fechaEmision,
            Integer horasTotales,
            String codigoVerificacion) {
    }

    public record GenerarCertificadoRequest(Long idEstudiante) {
    }
}
