package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;

import java.util.List;

public interface ExperienciaServiceInterface {

     List<Experiencia> listaExperiencia();

     Experiencia listarPorId(Integer id);

     List<Experiencia> listarPorHojaDeVida(Integer idHojaDeVida);

     Experiencia insertarExperiencia(Experiencia experiencia);
}
