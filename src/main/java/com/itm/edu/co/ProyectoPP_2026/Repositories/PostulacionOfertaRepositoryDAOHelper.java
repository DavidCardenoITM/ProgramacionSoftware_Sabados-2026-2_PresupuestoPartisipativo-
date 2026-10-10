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
}