package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.ActualizarOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.CrearOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.OfertaResumen;

import java.util.List;

public interface OfertaJpaServiceInterface {

    OfertaResumen crear(CrearOfertaRequest request, Integer idUsuario);

    OfertaResumen actualizar(Integer id, ActualizarOfertaRequest request, Integer idUsuario);

    OfertaResumen publicar(Integer id, Integer idUsuario);

    OfertaResumen cerrar(Integer id, Integer idUsuario);

    List<OfertaResumen> buscar(Integer idEntidad, Integer idMunicipio, String estado, String texto);

    OfertaResumen obtener(Integer id);
}
