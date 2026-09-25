package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class HojaDeVidaRepositoryDAOHelper {

    public String listarHojasDeVida() {
        return "select id, id_estudiante, resumen, foto_url, fecha_actualizacion from hoja_de_vida";
    }

    public String listarPorId() {
        return "select id, id_estudiante, resumen, foto_url, fecha_actualizacion "
                + "from hoja_de_vida where id = ?";
    }

    public String listarPorEstudiante() {
        // uq_hoja_estudiante es UNIQUE: a lo sumo una hoja de vida por estudiante (relación 1:1)
        return "select id, id_estudiante, resumen, foto_url, fecha_actualizacion "
                + "from hoja_de_vida where id_estudiante = ?";
    }

    public String insertarHojaDeVida() {
        return "insert into hoja_de_vida (id_estudiante, resumen, foto_url, fecha_actualizacion) "
                + "values (?, ?, ?, ?)";
    }
}
