package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificacionRepository {

    @Autowired
    private NotificacionRepositoryDAOHelper helper;

    public List<Notificacion> listarNotificacion(){
        List<Notificacion> notificacionList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarNotificaciones());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Notificacion notificacion = Notificacion.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .tipo(rs.getString("tipo"))
                        .mensaje(rs.getString("mensaje"))
                        .fecha(rs.getObject("fecha",java.time.LocalDate.class))
                        .leido(rs.getBoolean("leido"))
                        .build();
                notificacionList.add(notificacion);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return notificacionList;
    }

    public Notificacion listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Notificacion notificacionId = Notificacion.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                notificacionId = Notificacion.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .tipo(rs.getString("tipo"))
                        .mensaje(rs.getString("mensaje"))
                        .fecha(rs.getObject("fecha",java.time.LocalDate.class))
                        .leido(rs.getBoolean("leido"))
                        .build();
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
        return notificacionId;
    }

    public Notificacion insertarNotificacion(Notificacion notificacion) {
        Notificacion result = notificacion;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarNotificacion());
            ps.setInt(1, notificacion.getIdUsuario());
            ps.setString(2, notificacion.getTipo());
            ps.setString(3, notificacion.getMensaje());
            ps.setDate(4, java.sql.Date.valueOf(notificacion.getFecha()));
            ps.setBoolean(5, notificacion.getLeido() != null ? notificacion.getLeido() : false);
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.listarPorUsuario());
            ps.setInt(1, notificacion.getIdUsuario());
            ResultSet rs = ps.executeQuery();

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

    public Notificacion actualizarNotificacion(Notificacion notificacion) {
        Notificacion result = notificacion;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarNotificacion());
            ps.setInt(1, notificacion.getIdUsuario());
            ps.setString(2, notificacion.getTipo());
            ps.setString(3, notificacion.getMensaje());
            ps.setDate(4, java.sql.Date.valueOf(notificacion.getFecha()));
            ps.setBoolean(5, notificacion.getLeido());
            ps.setInt(6, notificacion.getId());
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

    public Boolean marcarLeido(Integer id, Boolean leido) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.marcarLeido());
            ps.setBoolean(1, leido);
            ps.setInt(2, id);
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

    public Boolean eliminarNotificacion(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarNotificacion());
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