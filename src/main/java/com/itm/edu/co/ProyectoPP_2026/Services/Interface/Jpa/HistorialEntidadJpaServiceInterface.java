package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HistorialDtos.HistorialEstudianteResumen;

import java.util.List;

public interface HistorialEntidadJpaServiceInterface {

    List<HistorialEstudianteResumen> listar(Integer idEntidad, Integer idUsuario);
}
