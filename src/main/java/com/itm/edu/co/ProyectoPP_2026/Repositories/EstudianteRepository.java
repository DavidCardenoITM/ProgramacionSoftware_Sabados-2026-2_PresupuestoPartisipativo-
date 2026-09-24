package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
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
public class EstudianteRepository {

    @Autowired
    private EstudianteRepositoryDAOHelper helper;

    public List<Estudiante> listarEstudiante(){
        List<Estudiante> estudianteList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarEstudiantes());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Estudiante estudiante = Estudiante.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .documentoIdentidad(rs.getString("documento_identidad"))
                        .idMunicipio(rs.getInt("id_municipio"))
                        .universidad(rs.getString("universidad"))
                        .build();
                estudianteList.add(estudiante);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return estudianteList;
    }

    public Estudiante listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Estudiante estudianteId = Estudiante.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                estudianteId = Estudiante.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .documentoIdentidad(rs.getString("documento_identidad"))
                        .idMunicipio(rs.getInt("id_municipio"))
                        .universidad(rs.getString("universidad"))
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
        return estudianteId;
    }

    public Estudiante insertarEstudiante(Estudiante estudiante) {
        Estudiante result = Estudiante.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarEstudiante());
            ps.setInt(1, estudiante.getIdUsuario());
            ps.setString(2, estudiante.getDocumentoIdentidad());
            ps.setInt(3, estudiante.getIdMunicipio());
            ps.setString(4, estudiante.getUniversidad());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorDocumento());
            ps.setString(1, estudiante.getDocumentoIdentidad());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =
                        Estudiante.builder()
                                .id(rs.getInt("id"))
                                .idUsuario(rs.getInt("id_usuario"))
                                .documentoIdentidad(rs.getString("documento_identidad"))
                                .idMunicipio(rs.getInt("id_municipio"))
                                .universidad(rs.getString("universidad"))
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

    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        Estudiante result = estudiante;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarEstudiante());
            ps.setInt(1, estudiante.getIdUsuario());
            ps.setString(2, estudiante.getDocumentoIdentidad());
            ps.setInt(3, estudiante.getIdMunicipio());
            ps.setString(4, estudiante.getUniversidad());
            ps.setInt(5, estudiante.getId());
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

    public Boolean eliminarEstudiante(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarEstudiante());
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
