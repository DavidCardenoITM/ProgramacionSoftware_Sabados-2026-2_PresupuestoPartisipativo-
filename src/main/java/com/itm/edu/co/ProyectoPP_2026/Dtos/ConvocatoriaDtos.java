package com.itm.edu.co.ProyectoPP_2026.Dtos;

import java.time.LocalDate;

public final class ConvocatoriaDtos {

    private ConvocatoriaDtos() {
    }

    public record CrearConvocatoriaRequest(
            Integer idMunicipio,
            String nombre,
            LocalDate fechaApertura,
            LocalDate fechaCierre,
            Integer cupos) {
    }

    public record ConvocatoriaResumen(
            Integer id,
            Integer idMunicipio,
            String nombre,
            LocalDate fechaApertura,
            LocalDate fechaCierre,
            Integer cupos,
            String estado,
            long postulacionesAceptadas) {
    }

    public record PostularConvocatoriaRequest(Integer idEstudiante) {
    }

    public record DecisionConvocatoriaRequest(String decision, String motivo) {
    }

    public record PostulacionConvocatoriaResumen(
            Integer id,
            Integer idEstudiante,
            Integer idConvocatoria,
            LocalDate fechaPostulacion,
            String estado,
            String motivoRechazo) {
    }
}
