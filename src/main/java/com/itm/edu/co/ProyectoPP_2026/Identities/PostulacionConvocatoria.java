package com.itm.edu.co.ProyectoPP_2026.Identities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "postulacion_convocatoria")
public class PostulacionConvocatoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_estudiante", nullable = false)
    private Integer idEstudiante;

    @Column(name = "id_convocatoria", nullable = false)
    private Integer idConvocatoria;

    @Column(name = "fecha_postulacion", nullable = false)
    private LocalDate fechaPostulacion;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "motivo_rechazo", columnDefinition = "text")
    private String motivoRechazo;
}
