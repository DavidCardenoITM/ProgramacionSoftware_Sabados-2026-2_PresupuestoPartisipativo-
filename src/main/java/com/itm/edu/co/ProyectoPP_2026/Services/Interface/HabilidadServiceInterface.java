package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;

import java.util.List;

public interface HabilidadServiceInterface {

     List<Habilidad> listaHabilidad();

     Habilidad listarPorId(Integer id);

     Habilidad insertarHabilidad(Habilidad habilidad);
}
