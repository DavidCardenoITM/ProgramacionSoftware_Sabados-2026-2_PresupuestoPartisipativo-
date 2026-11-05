package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CuentaDtos.CuentaResumen;

import java.util.List;

public interface CuentaJpaServiceInterface {

    List<CuentaResumen> listar(String rol, String estado, String texto);

    CuentaResumen obtener(Integer id);

    CuentaResumen cambiarEstado(Integer id, String estado, Integer idUsuario);

    CuentaResumen cambiarRol(Integer id, String rol, Integer idUsuario);
}
