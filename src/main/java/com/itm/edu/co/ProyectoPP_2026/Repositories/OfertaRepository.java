package com.itm.edu.co.ProyectoPP_2026.repositories;

import com.itm.edu.co.ProyectoPP_2026.identities.OfertaLaborSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfertaRepository {

    @Autowired
    private OfertaRepositoryDAOHelper helper;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/presupuesto_participativo}")
    private String dbUrl;

    @Value("${spring.datasource.username:root}")
    private String dbUser;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public List<OfertaLaborSocial> listarOfertas() {
        List<OfertaLaborSocial> result = new ArrayList<>();
        try (Connection connection = obtenerConexion();
             PreparedStatement ps = connection.prepareStatement(helper.listarOfertas());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OfertaLaborSocial oferta = OfertaLaborSocial.builder()
                        .id(rs.getInt("id"))
                        .idEntidad(rs.getInt("id_entidad"))
                        .titulo(rs.getString("titulo"))
                        .descripcion(rs.getString("descripcion"))
                        .cuposDisponibles(rs.getInt("cupos_disponibles"))
                        .horasOfrecidas(rs.getInt("horas_ofrecidas"))
                        .horario(rs.getString("horario"))
                        .ubicacion(rs.getString("ubicacion"))
                        .estado(rs.getString("estado"))
                        .build();
                result.add(oferta);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    public OfertaLaborSocial buscarPorId(Integer id) {
        OfertaLaborSocial result = null;
        try (Connection connection = obtenerConexion();
             PreparedStatement ps = connection.prepareStatement(helper.buscarPorId())) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = OfertaLaborSocial.builder()
                            .id(rs.getInt("id"))
                            .idEntidad(rs.getInt("id_entidad"))
                            .titulo(rs.getString("titulo"))
                            .descripcion(rs.getString("descripcion"))
                            .cuposDisponibles(rs.getInt("cupos_disponibles"))
                            .horasOfrecidas(rs.getInt("horas_ofrecidas"))
                            .horario(rs.getString("horario"))
                            .ubicacion(rs.getString("ubicacion"))
                            .estado(rs.getString("estado"))
                            .build();
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return result;
    }

    public Boolean insertarOferta(OfertaLaborSocial oferta) {
        try (Connection connection = obtenerConexion();
             PreparedStatement ps = connection.prepareStatement(helper.insertarOferta())) {

            ps.setInt(1, oferta.getIdEntidad());
            ps.setString(2, oferta.getTitulo());
            ps.setString(3, oferta.getDescripcion());
            ps.setInt(4, oferta.getCuposDisponibles());
            ps.setInt(5, oferta.getHorasOfrecidas());
            ps.setString(6, oferta.getHorario());
            ps.setString(7, oferta.getUbicacion());
            ps.setString(8, oferta.getEstado());
            ps.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
        try (Connection connection = obtenerConexion();
             PreparedStatement ps = connection.prepareStatement(helper.actualizarOferta())) {

            ps.setString(1, oferta.getTitulo());
            ps.setString(2, oferta.getDescripcion());
            ps.setInt(3, oferta.getCuposDisponibles());
            ps.setInt(4, oferta.getHorasOfrecidas());
            ps.setString(5, oferta.getHorario());
            ps.setString(6, oferta.getUbicacion());
            ps.setString(7, oferta.getEstado());
            ps.setInt(8, oferta.getId());
            ps.executeUpdate();
            return oferta;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public Boolean eliminarOferta(Integer id) {
        try (Connection connection = obtenerConexion();
             PreparedStatement ps = connection.prepareStatement(helper.eliminarOferta())) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}