package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class ValidacionFirmaRepositoryDAOHelper {

    public String listarValidaciones() {
        return "select id, id_registrohoras, id_representante, fecha_validacion, resultado, observaciones "
                + "from validacion_firma";
    }

    public String listarPorId() {
        return "select id, id_registrohoras, id_representante, fecha_validacion, resultado, observaciones "
                + "from validacion_firma where id = ?";
    }

    public String listarPorRegistroHoras() {
        // uq_validacion_registro es UNIQUE: a lo sumo una fila por registro de horas.
        return "select id, id_registrohoras, id_representante, fecha_validacion, resultado, observaciones "
                + "from validacion_firma where id_registrohoras = ?";
    }

    public String insertarValidacion() {
        return "insert into validacion_firma "
                + "(id_registrohoras, id_representante, fecha_validacion, resultado, observaciones) "
                + "values (?, ?, ?, ?, ?)";
    }
}