package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
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
public class ConvocatoriaRepository {

    @Autowired
    private ConvocatoriaRepositoryDAOHelper helper;

    private Convocatoria mapear(ResultSet rs) throws SQLException {
        return Convocatoria.builder()
                .id(rs.getInt("id"))
                .idMunicipio(rs.getInt("id_municipio"))
                .nombre(rs.getString("nombre"))
                .fechaApertura(rs.getObject("fecha_apertura", LocalDate.class))
                .fechaCierre(rs.getObject("fecha_cierre", LocalDate.class))
                .cupos(rs.getInt("cupos"))
                .estado(rs.getString("estado"))
                .build();
    }

    public List<Convocatoria> listarConvocatoria(){
        List<Convocatoria> list = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarConvocatorias());
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

    public Convocatoria listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Convocatoria result = Convocatoria.builder().build();
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

    public Convocatoria insertarConvocatoria(Convocatoria convocatoria) {
        Convocatoria result = Convocatoria.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarConvocatoria());
            ps.setInt(1, convocatoria.getIdMunicipio());
            ps.setString(2, convocatoria.getNombre());
            ps.setDate(3, java.sql.Date.valueOf(convocatoria.getFechaApertura()));
            ps.setDate(4, java.sql.Date.valueOf(convocatoria.getFechaCierre()));
            ps.setInt(5, convocatoria.getCupos());
            ps.setString(6, convocatoria.getEstado());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorNombre());
            ps.setString(1, convocatoria.getNombre());
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

    public Convocatoria actualizarConvocatoria(Convocatoria convocatoria) {
        Convocatoria result = convocatoria;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarConvocatoria());
            ps.setInt(1, convocatoria.getIdMunicipio());
            ps.setString(2, convocatoria.getNombre());
            ps.setDate(3, java.sql.Date.valueOf(convocatoria.getFechaApertura()));
            ps.setDate(4, java.sql.Date.valueOf(convocatoria.getFechaCierre()));
            ps.setInt(5, convocatoria.getCupos());
            ps.setString(6, convocatoria.getEstado());
            ps.setInt(7, convocatoria.getId());
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

    public Boolean eliminarConvocatoria(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarConvocatoria());
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
