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
}