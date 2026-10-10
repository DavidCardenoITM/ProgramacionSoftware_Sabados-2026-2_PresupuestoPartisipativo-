package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EstudianteRepositoryInterface {
     List<Estudiante> listarEstudiante();

     Estudiante listarPorId(Integer id);

     Estudiante insertarEstudiante(Estudiante estudiante);

     Estudiante actualizarEstudiante(Estudiante estudiante);

     Boolean eliminarEstudiante(Integer id);
}
