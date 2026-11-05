package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class HorasDtos {

    private HorasDtos() {
    }

    public record RegistrarHorasRequest(
            Long idPostulacionOferta,
            LocalDate fecha,
            String horaInicio,
            String horaFin,
            Double horasTrabajadas,
            String descripcionActividad) {
    }

    public record ValidarHorasRequest(String decision, String observaciones) {
    }

    public record RegistroHorasResumen(
            Long id,
            Long idPostulacionOferta,
            LocalDate fecha,
            String horaInicio,
            String horaFin,
            Double horasTrabajadas,
            String descripcionActividad,
            String estadoValidacion) {
    }

    public record ResumenHoras(
            Long idPostulacionOferta,
            Integer horasRequeridas,
            Double horasValidadas,
            Double horasPendientes,
            Double horasRechazadas,
            Double horasRestantes,
            boolean completo) {
    }
}
