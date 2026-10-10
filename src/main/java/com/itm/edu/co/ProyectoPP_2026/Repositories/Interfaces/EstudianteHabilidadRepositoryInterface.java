package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EstudianteHabilidadRepositoryInterface {

     List<EstudianteHabilidad> listarEstudianteHabilidad();

    /** Todas las habilidades registradas para un estudiante. */
     List<EstudianteHabilidad> listarPorEstudiante(Integer idEstudiante);

     EstudianteHabilidad insertarEstudianteHabilidad(EstudianteHabilidad eh);

    /** @return true si se borró una fila, false si el par no existía. */
     boolean eliminarEstudianteHabilidad(Integer idEstudiante, Integer idHabilidad);
}
