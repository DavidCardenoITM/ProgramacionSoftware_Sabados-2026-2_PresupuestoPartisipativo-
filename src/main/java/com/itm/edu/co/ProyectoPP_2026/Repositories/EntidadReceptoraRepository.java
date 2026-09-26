package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
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
public class EntidadReceptoraRepository {

    @Autowired
    private EntidadReceptoraRepositoryDAOHelper helper;

    private EntidadReceptora mapear(ResultSet rs) throws SQLException {
        return EntidadReceptora.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .nit(rs.getString("nit"))
                .sector(rs.getString("sector"))
                .direccion(rs.getString("direccion"))
                .idMunicipio(rs.getInt("id_municipio"))
                .estadoValidacion(rs.getString("estado_validacion"))
                .build();
    }

    public List<EntidadReceptora> listarEntidad(){
        List<EntidadReceptora> list = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarEntidades());
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

    public EntidadReceptora listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        EntidadReceptora result = EntidadReceptora.builder().build();
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

    public EntidadReceptora insertarEntidad(EntidadReceptora entidad) {
        EntidadReceptora result = EntidadReceptora.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarEntidad());
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getNit());
            ps.setString(3, entidad.getSector());
            ps.setString(4, entidad.getDireccion());
            ps.setInt(5, entidad.getIdMunicipio());
            ps.setString(6, entidad.getEstadoValidacion());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorNit());
            ps.setString(1, entidad.getNit());
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

    public EntidadReceptora actualizarEntidad(EntidadReceptora entidad) {
        EntidadReceptora result = entidad;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarEntidad());
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getNit());
            ps.setString(3, entidad.getSector());
            ps.setString(4, entidad.getDireccion());
            ps.setInt(5, entidad.getIdMunicipio());
            ps.setString(6, entidad.getEstadoValidacion());
            ps.setInt(7, entidad.getId());
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

    public Boolean eliminarEntidad(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarEntidad());
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
