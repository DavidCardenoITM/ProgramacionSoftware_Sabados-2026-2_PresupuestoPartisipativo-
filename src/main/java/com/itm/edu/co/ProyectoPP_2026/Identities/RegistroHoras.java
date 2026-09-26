package com.itm.edu.co.ProyectoPP_2026.Identities;

import java.time.LocalDate;

public class RegistroHoras {
    private Long id;
    private Long idPostulacionOferta;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private Double horasTrabajadas;
    private String descripcionActividad;
    private String estadoValidacion;

    public RegistroHoras() {}

    public RegistroHoras(Long id, Long idPostulacionOferta, LocalDate fecha, String horaInicio, String horaFin, Double horasTrabajadas, String descripcionActividad, String estadoValidacion) {
        this.id = id;
        this.idPostulacionOferta = idPostulacionOferta;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.horasTrabajadas = horasTrabajadas;
        this.descripcionActividad = descripcionActividad;
        this.estadoValidacion = estadoValidacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPostulacionOferta() { return idPostulacionOferta; }
    public void setIdPostulacionOferta(Long idPostulacionOferta) { this.idPostulacionOferta = idPostulacionOferta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public Double getHorasTrabajadas() { return horasTrabajadas; }
    public void setHorasTrabajadas(Double horasTrabajadas) { this.horasTrabajadas = horasTrabajadas; }

    public String getDescripcionActividad() { return descripcionActividad; }
    public void setDescripcionActividad(String descripcionActividad) { this.descripcionActividad = descripcionActividad; }

    public String getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(String estadoValidacion) { this.estadoValidacion = estadoValidacion; }
}