package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificadoRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public List<Certificado> findAll() {
        List<Certificado> lista = new ArrayList<>();
        String sql = "SELECT * FROM CERTIFICADO";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Certificado cert = new Certificado(
                    rs.getLong("id"),
                    rs.getLong("id_estudiante"),
                    rs.getDate("fecha_emision") != null ? rs.getDate("fecha_emision").toLocalDate() : null,
                    rs.getInt("horas_totales"),
                    rs.getString("codigo_verificacion")
                );
                lista.add(cert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(Certificado cert) {
        String sql = "INSERT INTO CERTIFICADO (id_estudiante, fecha_emision, horas_totales, codigo_verificacion) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cert.getIdEstudiante());
            pstmt.setDate(2, cert.getFechaEmision() != null ? Date.valueOf(cert.getFechaEmision()) : null);
            pstmt.setInt(3, cert.getHorasTotales());
            pstmt.setString(4, cert.getCodigoVerificacion());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}