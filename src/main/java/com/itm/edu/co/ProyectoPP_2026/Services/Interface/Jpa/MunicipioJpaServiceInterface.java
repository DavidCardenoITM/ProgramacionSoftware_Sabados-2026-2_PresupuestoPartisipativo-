package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.ActualizarMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.CrearMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.MunicipioResumen;

import java.util.List;

public interface MunicipioJpaServiceInterface {

    List<MunicipioResumen> listar();

    MunicipioResumen crear(CrearMunicipioRequest request, Integer idUsuario);

    MunicipioResumen actualizar(Integer id, ActualizarMunicipioRequest request, Integer idUsuario);
}
