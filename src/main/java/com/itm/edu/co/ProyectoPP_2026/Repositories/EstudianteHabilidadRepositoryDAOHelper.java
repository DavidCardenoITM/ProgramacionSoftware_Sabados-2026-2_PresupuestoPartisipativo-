package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class EstudianteHabilidadRepositoryDAOHelper {

    public String listarEstudianteHabilidad() {
        return "select id_estudiante, id_habilidad from estudiante_habilidad";
    }

    /** Todas las habilidades asociadas a un estudiante concreto. */
    public String listarPorEstudiante() {
        return "select id_estudiante, id_habilidad from estudiante_habilidad where id_estudiante = ?";
    }

    public String insertarEstudianteHabilidad() {
        return "insert into estudiante_habilidad (id_estudiante, id_habilidad) values (?, ?)";
    }

    /** No hay UPDATE con sentido aquí: la fila ES la relación; se borra e inserta de nuevo si cambia. */
    public String eliminarEstudianteHabilidad() {
        return "delete from estudiante_habilidad where id_estudiante = ? and id_habilidad = ?";
    }
}
