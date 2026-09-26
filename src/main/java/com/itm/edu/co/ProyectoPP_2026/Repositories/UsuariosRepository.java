package com.itm.edu.co.ProyectoPP_2026.Repositories;

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
public class UsuariosRepository {

    @Autowired
    private UsuariosRepositoryDAOHelper helper;

    public List<Usuarios> listarUsuario(){
        List<Usuarios> usuariosList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarUsuarios());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuarios usuarios = Usuarios.builder()
                        .id(rs.getInt("id"))
                        .nombres(rs.getString("nombres"))
                        .apellidos(rs.getString("apellidos"))
                        .correo(rs.getString("correo"))
                        .contrasenaHash(rs.getString("contrasena_hash"))
                        .rol(rs.getString("rol"))
                        .fechaRegistro(rs.getObject("fecha_registro",java.time.LocalDate.class))
                        .estado(rs.getString("estado"))
                        .build();
                usuariosList.add(usuarios);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return usuariosList;
    }

    public Usuarios listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Usuarios usuarioId = Usuarios.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuarioId = Usuarios.builder()
                        .id(rs.getInt("id"))
                        .nombres(rs.getString("nombres"))
                        .apellidos(rs.getString("apellidos"))
                        .correo(rs.getString("correo"))
                        .contrasenaHash(rs.getString("contrasena_hash"))
                        .rol(rs.getString("rol"))
                        .fechaRegistro(rs.getObject("fecha_registro",java.time.LocalDate.class))
                        .estado(rs.getString("estado"))
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
        return usuarioId;
    }

    public Usuarios insertarUsuario(Usuarios usuario) {
        Usuarios result = Usuarios.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarUsuario());
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasenaHash());
            ps.setString(5, usuario.getRol());
            ps.setDate(6,java.sql.Date.valueOf(usuario.getFechaRegistro()));
            ps.setString(7, usuario.getEstado());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorCorreo());
            ps.setString(1, usuario.getCorreo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =
                        Usuarios.builder()
                                .id(rs.getInt("id"))
                                .nombres(rs.getString("nombres"))
                                .apellidos(rs.getString("apellidos"))
                                .correo(rs.getString("correo"))
                                .contrasenaHash(rs.getString("contrasena_hash"))
                                .rol(rs.getString("rol"))
                                .fechaRegistro(rs.getObject("fecha_registro",java.time.LocalDate.class))
                                .estado(rs.getString("estado"))
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

    public Usuarios actualizarUsuario(Usuarios usuario) {
        Usuarios result = usuario;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarUsuario());
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasenaHash());
            ps.setString(5, usuario.getRol());
            ps.setDate(6,java.sql.Date.valueOf(usuario.getFechaRegistro()));
            ps.setString(7, usuario.getEstado());
            ps.setInt(8, usuario.getId());
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

    public Usuarios corregirCorreo(String correo, String nuevaCorreo) {
        Usuarios result = Usuarios.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.corregirCorreo());
            ps.setString(1, nuevaCorreo);
            ps.setString(2, correo);
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorCorreo());
            ps.setString(1, nuevaCorreo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =
                        Usuarios.builder()
                                .id(rs.getInt("id"))
                                .nombres(rs.getString("nombres"))
                                .apellidos(rs.getString("apellidos"))
                                .correo(rs.getString("correo"))
                                .contrasenaHash(rs.getString("contrasena_hash"))
                                .rol(rs.getString("rol"))
                                .fechaRegistro(rs.getObject("fecha_registro",java.time.LocalDate.class))
                                .estado(rs.getString("estado"))
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

    public Boolean eliminarUsuario(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarUsuario());
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
