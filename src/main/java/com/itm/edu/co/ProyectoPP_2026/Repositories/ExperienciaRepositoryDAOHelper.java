package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class ExperienciaRepositoryDAOHelper {

    public String listarExperiencias() {
        return "select id, id_hojadevida, tipo, institucion, cargo_titulo, fecha_inicio, fecha_fin, descripcion "
                + "from experiencia";
    }

    public String listarPorId() {
        return "select id, id_hojadevida, tipo, institucion, cargo_titulo, fecha_inicio, fecha_fin, descripcion "
                + "from experiencia where id = ?";
    }

    public String listarPorHojaDeVida() {
        // Una hoja de vida puede tener VARIAS experiencias (1:N), a diferencia de hoja_de_vida<->estudiante
        return "select id, id_hojadevida, tipo, institucion, cargo_titulo, fecha_inicio, fecha_fin, descripcion "
                + "from experiencia where id_hojadevida = ? order by fecha_inicio desc";
    }

    public String insertarExperiencia() {
        return "insert into experiencia "
                + "(id_hojadevida, tipo, institucion, cargo_titulo, fecha_inicio, fecha_fin, descripcion) "
                + "values (?, ?, ?, ?, ?, ?, ?)";
    }
}