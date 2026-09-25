package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
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
public class AdministradorRepository {

    @Autowired
    private AdministradorRepositoryDAOHelper helper;

    public List<Administrador> listarAdministrador(){
        List<Administrador> administradorList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarAdministradores());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Administrador administrador = Administrador.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .build();
                administradorList.add(administrador);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return administradorList;
    }

    public Administrador listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Administrador administradorId = Administrador.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                administradorId = Administrador.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
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
        return administradorId;
    }

    public Administrador insertarAdministrador(Administrador administrador) {
        Administrador result = Administrador.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarAdministrador());
            ps.setInt(1, administrador.getIdUsuario());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorIdUsuario());
            ps.setInt(1, administrador.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =
                        Administrador.builder()
                                .id(rs.getInt("id"))
                                .idUsuario(rs.getInt("id_usuario"))
                                .build();
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

    public Administrador actualizarAdministrador(Administrador administrador) {
        Administrador result = administrador;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarAdministrador());
            ps.setInt(1, administrador.getIdUsuario());
            ps.setInt(2, administrador.getId());
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

    public Boolean eliminarAdministrador(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarAdministrador());
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
