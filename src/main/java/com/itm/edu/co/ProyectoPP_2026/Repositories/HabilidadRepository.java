package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;
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
public class HabilidadRepository {

    @Autowired
    private HabilidadRepositoryDAOHelper helper;

    public List<Habilidad> listarHabilidad() {
        List<Habilidad> habilidadList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarHabilidades());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Habilidad habilidad = Habilidad.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .categoria(rs.getString("categoria"))
                        .build();
                habilidadList.add(habilidad);
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
        return habilidadList;
    }

    public Habilidad listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Habilidad habilidadId = Habilidad.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                habilidadId = Habilidad.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .categoria(rs.getString("categoria"))
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
        return habilidadId;
    }

    public Habilidad insertarHabilidad(Habilidad habilidad) {
        Habilidad result = habilidad;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarHabilidad());
            ps.setString(1, habilidad.getNombre());
            ps.setString(2, habilidad.getCategoria());
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
}
