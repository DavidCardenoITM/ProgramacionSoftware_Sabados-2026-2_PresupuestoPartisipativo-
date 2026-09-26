package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostulacionOfertaRepository {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/presupuesto_participativo?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<PostulacionOferta> findAll() {
        List<PostulacionOferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM POSTULACION_OFERTA";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PostulacionOferta postulacion = new PostulacionOferta(
                    rs.getLong("id"),
                    rs.getLong("id_estudiante"),
                    rs.getLong("id_oferta"),
                    rs.getDate("fecha_postulacion") != null ? rs.getDate("fecha_postulacion").toLocalDate() : null,
                    rs.getString("estado")
                );
                lista.add(postulacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(PostulacionOferta postulacion) {
        String sql = "INSERT INTO POSTULACION_OFERTA (id_estudiante, id_oferta, fecha_postulacion, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, postulacion.getIdEstudiante());
            pstmt.setLong(2, postulacion.getIdOferta());
            pstmt.setDate(3, postulacion.getFechaPostulacion() != null ? Date.valueOf(postulacion.getFechaPostulacion()) : null);
            pstmt.setString(4, postulacion.getEstado());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}