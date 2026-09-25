package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
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
public class RepresentanteEntidadRepository {

    @Autowired
    private RepresentanteEntidadRepositoryDAOHelper helper;

    private RepresentanteEntidad mapear(ResultSet rs) throws SQLException {
        return RepresentanteEntidad.builder()
                .id(rs.getInt("id"))
                .idUsuario(rs.getInt("id_usuario"))
                .idEntidad(rs.getInt("id_entidad"))
                .cargo(rs.getString("cargo"))
                .build();
    }

    public List<RepresentanteEntidad> listarRepresentante(){
        List<RepresentanteEntidad> list = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarRepresentantes());
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

    public RepresentanteEntidad listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        RepresentanteEntidad result = RepresentanteEntidad.builder().build();
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

    public RepresentanteEntidad insertarRepresentante(RepresentanteEntidad representante) {
        RepresentanteEntidad result = RepresentanteEntidad.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarRepresentante());
            ps.setInt(1, representante.getIdUsuario());
            ps.setInt(2, representante.getIdEntidad());
            ps.setString(3, representante.getCargo());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorIdUsuario());
            ps.setInt(1, representante.getIdUsuario());
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

    public RepresentanteEntidad actualizarRepresentante(RepresentanteEntidad representante) {
        RepresentanteEntidad result = representante;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarRepresentante());
            ps.setInt(1, representante.getIdUsuario());
            ps.setInt(2, representante.getIdEntidad());
            ps.setString(3, representante.getCargo());
            ps.setInt(4, representante.getId());
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

    public Boolean eliminarRepresentante(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarRepresentante());
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
