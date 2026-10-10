package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;

import java.util.List;

public interface PostulacionOfertaServiceInterface {

    List<PostulacionOferta> listarPostulaciones();

    PostulacionOferta buscarPorId(Long id);

    PostulacionOferta insertarPostulacion(PostulacionOferta postulacion);

    PostulacionOferta actualizarPostulacion(PostulacionOferta postulacion);

    Boolean eliminarPostulacion(Long id);
}
