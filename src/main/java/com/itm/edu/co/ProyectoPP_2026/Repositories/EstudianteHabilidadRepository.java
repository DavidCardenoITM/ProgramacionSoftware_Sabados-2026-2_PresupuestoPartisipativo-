package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A diferencia de las demás entidades, esta tabla NO tiene columna "id" propia:
 * su clave primaria es el par (id_estudiante, id_habilidad). Por eso este Repository
 * no tiene listarPorId(Integer id); en su lugar expone listarPorEstudiante(...) y
 * eliminar(...) recibiendo ambas claves.
 */
@Repository
public class EstudianteHabilidadRepository {

    @Autowired
    private EstudianteHabilidadRepositoryDAOHelper helper;

    public List<EstudianteHabilidad> listarEstudianteHabilidad() {
        List<EstudianteHabilidad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarEstudianteHabilidad());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return lista;
    }

    /** Todas las habilidades registradas para un estudiante. */
    public List<EstudianteHabilidad> listarPorEstudiante(Integer idEstudiante) {
        List<EstudianteHabilidad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorEstudiante());
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return lista;
    }

    public EstudianteHabilidad insertarEstudianteHabilidad(EstudianteHabilidad eh) {
        EstudianteHabilidad result = eh;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarEstudianteHabilidad());
            ps.setInt(1, eh.getIdEstudiante());
            ps.setInt(2, eh.getIdHabilidad());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return result;
    }

    /** @return true si se borró una fila, false si el par no existía. */
    public boolean eliminarEstudianteHabilidad(Integer idEstudiante, Integer idHabilidad) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarEstudianteHabilidad());
            ps.setInt(1, idEstudiante);
            ps.setInt(2, idHabilidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
    }

    private EstudianteHabilidad mapear(ResultSet rs) throws SQLException {
        return EstudianteHabilidad.builder()
                .idEstudiante(rs.getInt("id_estudiante"))
                .idHabilidad(rs.getInt("id_habilidad"))
                .build();
    }
}