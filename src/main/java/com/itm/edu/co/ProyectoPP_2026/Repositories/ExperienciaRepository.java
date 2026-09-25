package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
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
public class ExperienciaRepository {

    @Autowired
    private ExperienciaRepositoryDAOHelper helper;

    public List<Experiencia> listarExperiencia() {
        List<Experiencia> experienciaList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarExperiencias());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                experienciaList.add(mapear(rs));
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
        return experienciaList;
    }

    public Experiencia listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Experiencia experienciaId = Experiencia.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                experienciaId = mapear(rs);
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
        return experienciaId;
    }

    /** Historial completo de experiencias de una hoja de vida (relación 1:N). */
    public List<Experiencia> listarPorHojaDeVida(Integer idHojaDeVida) {
        List<Experiencia> experienciaList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorHojaDeVida());
            ps.setInt(1, idHojaDeVida);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                experienciaList.add(mapear(rs));
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
        return experienciaList;
    }

    public Experiencia insertarExperiencia(Experiencia experiencia) {
        Experiencia result = experiencia;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarExperiencia());
            ps.setInt(1, experiencia.getIdHojaDeVida());
            ps.setString(2, experiencia.getTipo());
            ps.setString(3, experiencia.getInstitucion());
            ps.setString(4, experiencia.getCargoTitulo());
            ps.setDate(5, Date.valueOf(experiencia.getFechaInicio()));
            // fecha_fin es opcional: una experiencia en curso todavía no tiene fecha de cierre
            if (experiencia.getFechaFin() != null) {
                ps.setDate(6, Date.valueOf(experiencia.getFechaFin()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            if (experiencia.getDescripcion() != null) {
                ps.setString(7, experiencia.getDescripcion());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }
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

    private Experiencia mapear(ResultSet rs) throws SQLException {
        return Experiencia.builder()
                .id(rs.getInt("id"))
                .idHojaDeVida(rs.getInt("id_hojadevida"))
                .tipo(rs.getString("tipo"))
                .institucion(rs.getString("institucion"))
                .cargoTitulo(rs.getString("cargo_titulo"))
                .fechaInicio(rs.getObject("fecha_inicio", java.time.LocalDate.class))
                .fechaFin(rs.getObject("fecha_fin", java.time.LocalDate.class))
                .descripcion(rs.getString("descripcion"))
                .build();
    }
}