package com.itm.edu.co.ProyectoPP_2026.Identities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "estudiante_habilidad")
@IdClass(EstudianteHabilidadId.class)
public class EstudianteHabilidad {

    @Id
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Id
    @Column(name = "id_habilidad")
    private Integer idHabilidad;
}
