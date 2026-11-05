package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.DecisionOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostulacionOfertaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostularOfertaRequest;

import java.util.List;

public interface PostulacionOfertaJpaServiceInterface {

    PostulacionOfertaResumen postular(Long idOferta, PostularOfertaRequest request, Integer idUsuario);

    List<PostulacionOfertaResumen> listarPorEstudiante(Long idEstudiante, Integer idUsuario);

    List<PostulacionOfertaResumen> listarPorOferta(Long idOferta, String estado, Integer idUsuario);

    PostulacionOfertaResumen retirar(Long idPostulacion, Integer idUsuario);

    PostulacionOfertaResumen decidir(Long idPostulacion, DecisionOfertaRequest request, Integer idUsuario);

    PostulacionOfertaResumen asignar(Long idPostulacion, Integer idUsuario);
}
