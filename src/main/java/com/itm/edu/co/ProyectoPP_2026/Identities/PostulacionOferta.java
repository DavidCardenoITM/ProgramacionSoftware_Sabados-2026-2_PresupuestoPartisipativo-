package com.itm.edu.co.ProyectoPP_2026.Identities;

import java.time.LocalDate;

public class PostulacionOferta {
    private Long id;
    private Long idEstudiante;
    private Long idOferta;
    private LocalDate fechaPostulacion;
    private String estado;

    public PostulacionOferta() {}

    public PostulacionOferta(Long id, Long idEstudiante, Long idOferta, LocalDate fechaPostulacion, String estado) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idOferta = idOferta;
        this.fechaPostulacion = fechaPostulacion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Long idEstudiante) { this.idEstudiante = idEstudiante; }

    public Long getIdOferta() { return idOferta; }
    public void setIdOferta(Long idOferta) { this.idOferta = idOferta; }

    public LocalDate getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDate fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}