package com.itm.edu.co.ProyectoPP_2026.Identities;

import java.time.LocalDate;

public class Certificado {
    private Long id;
    private Long idEstudiante;
    private LocalDate fechaEmision;
    private Integer horasTotales;
    private String codigoVerificacion;

    public Certificado() {}

    public Certificado(Long id, Long idEstudiante, LocalDate fechaEmision, Integer horasTotales, String codigoVerificacion) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.fechaEmision = fechaEmision;
        this.horasTotales = horasTotales;
        this.codigoVerificacion = codigoVerificacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Long idEstudiante) { this.idEstudiante = idEstudiante; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public Integer getHorasTotales() { return horasTotales; }
    public void setHorasTotales(Integer horasTotales) { this.horasTotales = horasTotales; }

    public String getCodigoVerificacion() { return codigoVerificacion; }
    public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }
}