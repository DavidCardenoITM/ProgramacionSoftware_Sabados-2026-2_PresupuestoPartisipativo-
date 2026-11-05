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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "entidad_receptora")
public class EntidadReceptora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "nit", nullable = false, unique = true, length = 30)
    private String nit;

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "id_municipio", nullable = false)
    private Integer idMunicipio;

    @Column(name = "estado_validacion", nullable = false, length = 30)
    private String estadoValidacion;
}
