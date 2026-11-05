package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteHabilidadId implements Serializable {

    private Integer idEstudiante;

    private Integer idHabilidad;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstudianteHabilidadId that)) {
            return false;
        }
        return Objects.equals(idEstudiante, that.idEstudiante)
                && Objects.equals(idHabilidad, that.idHabilidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEstudiante, idHabilidad);
    }
}
