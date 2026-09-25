package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HojaDeVidaRepository {

    @Autowired
    private HojaDeVidaRepositoryDAOHelper helper;

    public List<HojaDeVida> listarHojaDeVida() {
        List<HojaDeVida> hojaList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarHojasDeVida());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HojaDeVida hoja = HojaDeVida.builder()
                        .id(rs.getInt("id"))
                        .idEstudiante(rs.getInt("id_estudiante"))
                        .resumen(rs.getString("resumen"))
                        .fotoUrl(rs.getString("foto_url"))
                        .fechaActualizacion(rs.getObject("fecha_actualizacion", java.time.LocalDate.class))
                        .build();
                hojaList.add(hoja);
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
        return hojaList;
    }

    public HojaDeVida listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        HojaDeVida hojaId = HojaDeVida.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hojaId = HojaDeVida.builder()
                        .id(rs.getInt("id"))
                        .idEstudiante(rs.getInt("id_estudiante"))
                        .resumen(rs.getString("resumen"))
                        .fotoUrl(rs.getString("foto_url"))
                        .fechaActualizacion(rs.getObject("fecha_actualizacion", java.time.LocalDate.class))
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
        return hojaId;
    }

    /** Relación 1:1 con Estudiante (uq_hoja_estudiante): a lo sumo una fila por estudiante. */
    public HojaDeVida listarPorEstudiante(Integer idEstudiante) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        HojaDeVida hoja = HojaDeVida.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorEstudiante());
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hoja = HojaDeVida.builder()
                        .id(rs.getInt("id"))
                        .idEstudiante(rs.getInt("id_estudiante"))
                        .resumen(rs.getString("resumen"))
                        .fotoUrl(rs.getString("foto_url"))
                        .fechaActualizacion(rs.getObject("fecha_actualizacion", java.time.LocalDate.class))
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
        return hoja;
    }

    public HojaDeVida insertarHojaDeVida(HojaDeVida hoja) {
        HojaDeVida result = hoja;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarHojaDeVida());
            ps.setInt(1, hoja.getIdEstudiante());
            // resumen y foto_url son opcionales en la tabla (sin NOT NULL)
            if (hoja.getResumen() != null) {
                ps.setString(2, hoja.getResumen());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (hoja.getFotoUrl() != null) {
                ps.setString(3, hoja.getFotoUrl());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            // Si no viene la fecha, se usa la fecha del servidor (igual que el DEFAULT de la tabla)
            ps.setDate(4, Date.valueOf(
                    hoja.getFechaActualizacion() != null
                            ? hoja.getFechaActualizacion()
                            : java.time.LocalDate.now()));
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
