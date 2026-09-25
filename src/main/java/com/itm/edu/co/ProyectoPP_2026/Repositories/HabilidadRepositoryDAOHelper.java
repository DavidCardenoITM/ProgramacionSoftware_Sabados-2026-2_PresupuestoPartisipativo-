package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class HabilidadRepositoryDAOHelper {

    public String listarHabilidades() {
        return "select id, nombre, categoria from habilidad";
    }

    public String listarPorId() {
        return "select id, nombre, categoria from habilidad where id = ?";
    }

    public String insertarHabilidad() {
        return "insert into habilidad (nombre, categoria) values (?, ?)";
    }
}