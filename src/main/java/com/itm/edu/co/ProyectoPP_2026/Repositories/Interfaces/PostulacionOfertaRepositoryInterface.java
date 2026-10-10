package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;

import java.util.List;

public interface PostulacionOfertaRepositoryInterface {
    List<PostulacionOferta> listarPostulaciones();
    PostulacionOferta buscarPorId(Long id);
    PostulacionOferta insertarPostulacion(PostulacionOferta postulacion);
    PostulacionOferta actualizarPostulacion(PostulacionOferta postulacion);
    Boolean eliminarPostulacion(Long id);
}
