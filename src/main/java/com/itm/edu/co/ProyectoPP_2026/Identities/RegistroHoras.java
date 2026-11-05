package com.itm.edu.co.ProyectoPP_2026.Identities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "registro_horas")
public class RegistroHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_postulacion_oferta", nullable = false)
    private Long idPostulacionOferta;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false, length = 8)
    private String horaInicio;

    @Column(name = "hora_fin", nullable = false, length = 8)
    private String horaFin;

    @Column(name = "horas_trabajadas", nullable = false)
    private Double horasTrabajadas;

    @Column(name = "descripcion_actividad", nullable = false, columnDefinition = "text")
    private String descripcionActividad;

    @Column(name = "estado_validacion", nullable = false, length = 30)
    private String estadoValidacion;

    public RegistroHoras() {
    }

    public RegistroHoras(Long id, Long idPostulacionOferta, LocalDate fecha, String horaInicio, String horaFin,
                         Double horasTrabajadas, String descripcionActividad, String estadoValidacion) {
        this.id = id;
        this.idPostulacionOferta = idPostulacionOferta;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.horasTrabajadas = horasTrabajadas;
        this.descripcionActividad = descripcionActividad;
        this.estadoValidacion = estadoValidacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPostulacionOferta() {
        return idPostulacionOferta;
    }

    public void setIdPostulacionOferta(Long idPostulacionOferta) {
        this.idPostulacionOferta = idPostulacionOferta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public String getEstadoValidacion() {
        return estadoValidacion;
    }

    public void setEstadoValidacion(String estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }
}
