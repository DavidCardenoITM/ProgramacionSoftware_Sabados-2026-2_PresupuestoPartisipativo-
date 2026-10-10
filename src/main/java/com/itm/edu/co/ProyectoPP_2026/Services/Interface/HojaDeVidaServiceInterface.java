package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;

import java.util.List;

public interface HojaDeVidaServiceInterface {

     List<HojaDeVida> listaHojaDeVida();

     HojaDeVida listarPorId(Integer id);

     HojaDeVida listarPorEstudiante(Integer idEstudiante);

     HojaDeVida insertarHojaDeVida(HojaDeVida hoja);

}
