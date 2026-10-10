package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class RegistroHorasRepositoryDAOHelper {

    public String listarRegistrosHoras() {
        return "SELECT id, id_postulacion_oferta, fecha, hora_inicio, hora_fin, " +
                "horas_trabajadas, descripcion_actividad, estado_validacion " +
                "FROM REGISTRO_HORAS";
    }

    public String insertarRegistroHoras() {
        return "INSERT INTO REGISTRO_HORAS " +
                "(id_postulacion_oferta, fecha, hora_inicio, hora_fin, " +
                "horas_trabajadas, descripcion_actividad, estado_validacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String listarPorId() {
        return "SELECT id, id_postulacion_oferta, fecha, hora_inicio, hora_fin, " +
                "horas_trabajadas, descripcion_actividad, estado_validacion " +
                "FROM REGISTRO_HORAS WHERE id = ?";
    }

    public String actualizarRegistroHoras() {
        return "UPDATE REGISTRO_HORAS SET id_postulacion_oferta = ?, fecha = ?, " +
                "hora_inicio = ?, hora_fin = ?, horas_trabajadas = ?, " +
                "descripcion_actividad = ?, estado_validacion = ? WHERE id = ?";
    }

    public String eliminarRegistroHoras() {
        return "DELETE FROM REGISTRO_HORAS WHERE id = ?";
    }
}