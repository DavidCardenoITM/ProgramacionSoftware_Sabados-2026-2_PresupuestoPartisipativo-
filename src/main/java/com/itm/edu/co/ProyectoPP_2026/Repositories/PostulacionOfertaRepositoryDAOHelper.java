package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class PostulacionOfertaRepositoryDAOHelper {

    public String listarPostulacionesOferta() {
        return "SELECT id, id_estudiante, id_oferta, fecha_postulacion, estado " +
                "FROM POSTULACION_OFERTA";
    }

    public String insertarPostulacionOferta() {
        return "INSERT INTO POSTULACION_OFERTA " +
                "(id_estudiante, id_oferta, fecha_postulacion, estado) " +
                "VALUES (?, ?, ?, ?)";
    }

    public String listarPorId() {
        return "SELECT id, id_estudiante, id_oferta, fecha_postulacion, estado " +
                "FROM POSTULACION_OFERTA WHERE id = ?";
    }

    public String actualizarPostulacionOferta() {
        return "UPDATE POSTULACION_OFERTA SET id_estudiante = ?, id_oferta = ?, " +
                "fecha_postulacion = ?, estado = ? WHERE id = ?";
    }

    public String eliminarPostulacionOferta() {
        return "DELETE FROM POSTULACION_OFERTA WHERE id = ?";
    }
}