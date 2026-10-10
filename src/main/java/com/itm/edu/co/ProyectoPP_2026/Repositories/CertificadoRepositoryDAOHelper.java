package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class CertificadoRepositoryDAOHelper {

    public String listarCertificados() {
        return "SELECT id, id_estudiante, fecha_emision, horas_totales, codigo_verificacion " +
                "FROM CERTIFICADO";
    }

    public String insertarCertificado() {
        return "INSERT INTO CERTIFICADO " +
                "(id_estudiante, fecha_emision, horas_totales, codigo_verificacion) " +
                "VALUES (?, ?, ?, ?)";
    }

    public String listarPorId() {
        return "SELECT id, id_estudiante, fecha_emision, horas_totales, codigo_verificacion " +
                "FROM CERTIFICADO WHERE id = ?";
    }

    public String buscarPorCodigo() {
        return "SELECT id, id_estudiante, fecha_emision, horas_totales, codigo_verificacion " +
                "FROM CERTIFICADO WHERE codigo_verificacion = ?";
    }

    public String actualizarCertificado() {
        return "UPDATE CERTIFICADO SET id_estudiante = ?, fecha_emision = ?, " +
                "horas_totales = ?, codigo_verificacion = ? WHERE id = ?";
    }

    public String eliminarCertificado() {
        return "DELETE FROM CERTIFICADO WHERE id = ?";
    }
}
