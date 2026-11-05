package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class HistorialDtos {

    private HistorialDtos() {
    }

    public record HistorialEstudianteResumen(
            Long idPostulacionOferta,
            Long idEstudiante,
            String nombreEstudiante,
            String documentoIdentidad,
            String tituloOferta,
            String estado,
            LocalDate fechaPostulacion,
            Double horasValidadas) {
    }
}
