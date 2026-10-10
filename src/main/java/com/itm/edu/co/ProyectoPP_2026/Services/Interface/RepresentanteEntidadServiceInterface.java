package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;

import java.util.List;

public interface RepresentanteEntidadServiceInterface {

     List<RepresentanteEntidad> listaRepresentante();

     RepresentanteEntidad listarPorId(Integer id);

     RepresentanteEntidad insertarRepresentante(RepresentanteEntidad representante);

     RepresentanteEntidad actualizarRepresentante(RepresentanteEntidad representante);

     Boolean eliminarRepresentante(Integer id);
}
