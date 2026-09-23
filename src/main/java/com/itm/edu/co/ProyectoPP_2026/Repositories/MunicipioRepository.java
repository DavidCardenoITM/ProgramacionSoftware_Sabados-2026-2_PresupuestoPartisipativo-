package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
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
public class MunicipioRepository {

    @Autowired
    private MunicipioRepositoryDAOHelper helper;

    public List<Municipio> listarMunicipio(){
        List<Municipio> municipioList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarMunicipios());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Municipio municipio = Municipio.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .codigoDane(rs.getString("codigo_dane"))
                        .build();
                municipioList.add(municipio);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return municipioList;
    }

    public Municipio listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Municipio municipioId = Municipio.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                municipioId = Municipio.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .codigoDane(rs.getString("codigo_dane"))
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
        return municipioId;
    }

    public Municipio insertarMunicipio(Municipio municipio) {
        Municipio result = Municipio.builder().build();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarMunicipio());
            ps.setString(1, municipio.getNombre());
            ps.setString(2, municipio.getCodigoDane());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.buscarPorCodigoDane());
            ps.setString(1, municipio.getCodigoDane());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =
                        Municipio.builder()
                                .id(rs.getInt("id"))
                                .nombre(rs.getString("nombre"))
                                .codigoDane(rs.getString("codigo_dane"))
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

    public Municipio actualizarMunicipio(Municipio municipio) {
        Municipio result = municipio;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.actualizarMunicipio());
            ps.setString(1, municipio.getNombre());
            ps.setString(2, municipio.getCodigoDane());
            ps.setInt(3, municipio.getId());
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

    public Boolean eliminarMunicipio(Integer id) {
        Boolean result = true;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {

            PreparedStatement ps = connection.prepareStatement(helper.eliminarMunicipio());
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