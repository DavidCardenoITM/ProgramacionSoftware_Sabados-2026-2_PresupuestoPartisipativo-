package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class OfertaDtos {

    private OfertaDtos() {
    }

    public record CrearOfertaRequest(
            Integer idEntidad,
            String titulo,
            String descripcion,
            Integer cuposDisponibles,
            Integer horasOfrecidas,
            String horario,
            String ubicacion,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Boolean publicar) {
    }

    public record ActualizarOfertaRequest(
            String titulo,
            String descripcion,
            Integer cuposDisponibles,
            Integer horasOfrecidas,
            String horario,
            String ubicacion,
            LocalDate fechaInicio,
            LocalDate fechaFin) {
    }

    public record OfertaResumen(
            Integer id,
            Integer idEntidad,
            String titulo,
            String descripcion,
            Integer cuposDisponibles,
            Integer horasOfrecidas,
            String horario,
            String ubicacion,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String estado) {
    }

    public record PostularOfertaRequest(Long idEstudiante) {
    }

    public record DecisionOfertaRequest(String decision) {
    }

    public record PostulacionOfertaResumen(
            Long id,
            Long idEstudiante,
            Long idOferta,
            LocalDate fechaPostulacion,
            String estado) {
    }
}
