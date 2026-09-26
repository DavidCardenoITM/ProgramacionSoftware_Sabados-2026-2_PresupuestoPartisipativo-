package com.itm.edu.co.ProyectoPP_2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class OfertaRepositoryDAOHelper {

    public String listarOfertas() {
        return "SELECT id, id_entidad, titulo, descripcion, cupos_disponibles, horas_ofrecidas, horario, ubicacion, estado FROM oferta_labor_social";
    }

    public String buscarPorId() {
        return "SELECT id, id_entidad, titulo, descripcion, cupos_disponibles, horas_ofrecidas, horario, ubicacion, estado FROM oferta_labor_social WHERE id = ?";
    }

    public String insertarOferta() {
        return "INSERT INTO oferta_labor_social (id_entidad, titulo, descripcion, cupos_disponibles, horas_ofrecidas, horario, ubicacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarOferta() {
        return "UPDATE oferta_labor_social SET titulo = ?, descripcion = ?, cupos_disponibles = ?, horas_ofrecidas = ?, horario = ?, ubicacion = ?, estado = ? WHERE id = ?";
    }

    public String eliminarOferta() {
        return "DELETE FROM oferta_labor_social WHERE id = ?";
    }
}