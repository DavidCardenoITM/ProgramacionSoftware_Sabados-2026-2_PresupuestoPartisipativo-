package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.ConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.CrearConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.DecisionConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostulacionConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostularConvocatoriaRequest;

import java.util.List;

public interface ConvocatoriaJpaServiceInterface {

    ConvocatoriaResumen crear(CrearConvocatoriaRequest request, Integer idUsuario);

    List<ConvocatoriaResumen> listar(Integer idMunicipio, String estado, boolean soloVigentes);

    ConvocatoriaResumen obtener(Integer id);

    ConvocatoriaResumen cerrar(Integer id, Integer idUsuario);

    PostulacionConvocatoriaResumen postular(Integer idConvocatoria, PostularConvocatoriaRequest request,
                                            Integer idUsuario);

    List<PostulacionConvocatoriaResumen> listarPostulaciones(Integer idConvocatoria, String estado,
                                                             Integer idUsuario);

    PostulacionConvocatoriaResumen decidir(Integer idPostulacion, DecisionConvocatoriaRequest request,
                                           Integer idUsuario);
}
