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
@Table(name = "experiencia")
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_hojadevida", nullable = false)
    private Integer idHojaDeVida;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "institucion", length = 150)
    private String institucion;

    @Column(name = "cargo_titulo", length = 100)
    private String cargoTitulo;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;
}
