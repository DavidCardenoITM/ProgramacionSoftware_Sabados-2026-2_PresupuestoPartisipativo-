package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;

import java.util.List;

public interface PostulacionConvocatoriaServiceinterface {

     List<PostulacionConvocatoria> listaPostulacion();

     PostulacionConvocatoria listarPorId(Integer id);

     PostulacionConvocatoria insertarPostulacion(PostulacionConvocatoria postulacion);

     PostulacionConvocatoria actualizarPostulacion(PostulacionConvocatoria postulacion);

     Boolean eliminarPostulacion(Integer id);
}
