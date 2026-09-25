package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostulacionConvocatoriaRepository {

    @Autowired
    private PostulacionConvocatoriaRepositoryDAOHelper helper;

    private PostulacionConvocatoria mapear(ResultSet rs) throws SQLException {
        return PostulacionConvocatoria.builder()
                .id(rs.getInt("id"))
                .idEstudiante(rs.getInt("id_estudiante"))
                .idConvocatoria(rs.getInt("id_convocatoria"))
                .fechaPostulacion(rs.getObject("fecha_postulacion", LocalDate.class))
                .estado(rs.getString("estado"))
                .motivoRechazo(rs.getString("motivo_rechazo"))
                .build();
    }

    public List<PostulacionConvocatoria> listarPostulacion(){
        List<PostulacionConvocatoria> list = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPostulaciones());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(mapear(rs));
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return list;
    }

    public PostulacionConvocatoria listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        PostulacionConvocatoria result = PostulacionConvocatoria.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result = mapear(rs);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {

            }
        }
        return result;
    }

    public PostulacionConvocatoria insertarPostulacion(PostulacionConvocatoria postulacion) {
        PostulacionConvocatoria result = PostulacionConvocatoria.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarPostulacion());
            ps.setInt(1, postulacion.getIdEstudiante());
            ps.setInt(2, postulacion.getIdConvocatoria());
            ps.setDate(3, java.sql.Date.valueOf(postulacion.getFechaPostulacion()));
            ps.setString(4, postulacion.getEstado());
            ps.setString(5, postulacion.getMotivoRechazo());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorEstudianteYConvocatoria());
            ps.setInt(1, postulacion.getIdEstudiante());
            ps.setInt(2, postulacion.getIdConvocatoria());
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

    public PostulacionConvocatoria actualizarPostulacion(PostulacionConvocatoria postulacion) {
        PostulacionConvocatoria result = postulacion;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarPostulacion());
            ps.setInt(1, postulacion.getIdEstudiante());
            ps.setInt(2, postulacion.getIdConvocatoria());
            ps.setDate(3, java.sql.Date.valueOf(postulacion.getFechaPostulacion()));
            ps.setString(4, postulacion.getEstado());
            ps.setString(5, postulacion.getMotivoRechazo());
            ps.setInt(6, postulacion.getId());
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

    public Boolean eliminarPostulacion(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarPostulacion());
            ps.setInt(1, id);
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
        return result;
    }

}
