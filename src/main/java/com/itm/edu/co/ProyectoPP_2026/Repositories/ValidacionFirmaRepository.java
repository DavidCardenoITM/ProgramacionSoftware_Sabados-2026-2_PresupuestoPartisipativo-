package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ValidacionFirmaRepository {

    @Autowired
    private ValidacionFirmaRepositoryDAOHelper helper;

    public List<ValidacionFirma> listarValidacionFirma() {
        List<ValidacionFirma> validacionList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarValidaciones());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ValidacionFirma validacion = ValidacionFirma.builder()
                        .id(rs.getInt("id"))
                        .idRegistroHoras(rs.getInt("id_registrohoras"))
                        .idRepresentante(rs.getInt("id_representante"))
                        .fechaValidacion(rs.getObject("fecha_validacion", java.time.LocalDateTime.class))
                        .resultado(rs.getString("resultado"))
                        .observaciones(rs.getString("observaciones"))
                        .build();
                validacionList.add(validacion);
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
        return validacionList;
    }

    public ValidacionFirma listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        ValidacionFirma validacionId = ValidacionFirma.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                validacionId = ValidacionFirma.builder()
                        .id(rs.getInt("id"))
                        .idRegistroHoras(rs.getInt("id_registrohoras"))
                        .idRepresentante(rs.getInt("id_representante"))
                        .fechaValidacion(rs.getObject("fecha_validacion", java.time.LocalDateTime.class))
                        .resultado(rs.getString("resultado"))
                        .observaciones(rs.getString("observaciones"))
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
        return validacionId;
    }

    public ValidacionFirma insertarValidacion(ValidacionFirma validacion) {
        ValidacionFirma result = validacion;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarValidacion());
            ps.setInt(1, validacion.getIdRegistroHoras());
            ps.setInt(2, validacion.getIdRepresentante());
            // fecha_validacion es DATETIME con DEFAULT CURRENT_TIMESTAMP; si el cliente no la
            // envía, se usa la hora del servidor.
            ps.setTimestamp(3, Timestamp.valueOf(
                    validacion.getFechaValidacion() != null
                            ? validacion.getFechaValidacion()
                            : java.time.LocalDateTime.now()));
            ps.setString(4, validacion.getResultado());
            ps.setString(5, validacion.getObservaciones());
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