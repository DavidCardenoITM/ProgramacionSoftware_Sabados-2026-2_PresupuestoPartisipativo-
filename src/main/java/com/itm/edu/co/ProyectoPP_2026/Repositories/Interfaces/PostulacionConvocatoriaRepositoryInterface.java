package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;

import java.util.List;

public interface PostulacionConvocatoriaRepositoryInterface {


     List<PostulacionConvocatoria> listarPostulacion();

     PostulacionConvocatoria listarPorId(Integer id);

     PostulacionConvocatoria insertarPostulacion(PostulacionConvocatoria postulacion);

     PostulacionConvocatoria actualizarPostulacion(PostulacionConvocatoria postulacion);

     Boolean eliminarPostulacion(Integer id);
}
