package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroHorasRepository {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/presupuesto_participativo?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<RegistroHoras> findAll() {
        List<RegistroHoras> lista = new ArrayList<>();
        String sql = "SELECT * FROM REGISTRO_HORAS";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RegistroHoras registro = new RegistroHoras(
                    rs.getLong("id"),
                    rs.getLong("id_postulacion_oferta"),
                    rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null,
                    rs.getString("hora_inicio"),
                    rs.getString("hora_fin"),
                    rs.getDouble("horas_trabajadas"),
                    rs.getString("descripcion_actividad"),
                    rs.getString("estado_validacion")
                );
                lista.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(RegistroHoras registro) {
        String sql = "INSERT INTO REGISTRO_HORAS (id_postulacion_oferta, fecha, hora_inicio, hora_fin, horas_trabajadas, descripcion_actividad, estado_validacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, registro.getIdPostulacionOferta());
            pstmt.setDate(2, registro.getFecha() != null ? Date.valueOf(registro.getFecha()) : null);
            pstmt.setString(3, registro.getHoraInicio());
            pstmt.setString(4, registro.getHoraFin());
            pstmt.setDouble(5, registro.getHorasTrabajadas());
            pstmt.setString(6, registro.getDescripcionActividad());
            pstmt.setString(7, registro.getEstadoValidacion());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}