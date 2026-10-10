package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.PostulacionOfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostulacionOfertaRepository implements PostulacionOfertaRepositoryInterface {

    @Autowired
    private PostulacionOfertaRepositoryDAOHelper helper;

    private PostulacionOferta mapear(ResultSet rs) throws SQLException {
        return new PostulacionOferta(
                rs.getLong("id"),
                rs.getLong("id_estudiante"),
                rs.getLong("id_oferta"),
                rs.getDate("fecha_postulacion") != null ? rs.getDate("fecha_postulacion").toLocalDate() : null,
                rs.getString("estado")
        );
    }

    @Override
    public List<PostulacionOferta> listarPostulaciones() {
        List<PostulacionOferta> result = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPostulacionesOferta());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapear(rs));
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
        return result;
    }

    @Override
    public PostulacionOferta buscarPorId(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        PostulacionOferta result = new PostulacionOferta();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = mapear(rs);
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
        return result;
    }

    @Override
    public PostulacionOferta insertarPostulacion(PostulacionOferta postulacion) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        PostulacionOferta result = new PostulacionOferta();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarPostulacionOferta(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, postulacion.getIdEstudiante());
            ps.setLong(2, postulacion.getIdOferta());
            ps.setDate(3, postulacion.getFechaPostulacion() != null ? Date.valueOf(postulacion.getFechaPostulacion()) : null);
            ps.setString(4, postulacion.getEstado());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                result = buscarPorId(keys.getLong(1));
            } else {
                result = postulacion;
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
        return result;
    }

    @Override
    public PostulacionOferta actualizarPostulacion(PostulacionOferta postulacion) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.actualizarPostulacionOferta());
            ps.setLong(1, postulacion.getIdEstudiante());
            ps.setLong(2, postulacion.getIdOferta());
            ps.setDate(3, postulacion.getFechaPostulacion() != null ? Date.valueOf(postulacion.getFechaPostulacion()) : null);
            ps.setString(4, postulacion.getEstado());
            ps.setLong(5, postulacion.getId());
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
        return postulacion;
    }

    @Override
    public Boolean eliminarPostulacion(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarPostulacionOferta());
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return true;
    }
}
