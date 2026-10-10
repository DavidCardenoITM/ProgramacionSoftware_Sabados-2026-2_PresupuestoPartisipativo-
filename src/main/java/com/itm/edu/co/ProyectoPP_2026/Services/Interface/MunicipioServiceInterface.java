package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;

import java.util.List;

public interface MunicipioServiceInterface {
     List<Municipio> listaMunicipio();

     Municipio listarPorId(Integer id);

     Municipio insertarMunicipio(Municipio municipio);

     Municipio actualizarMunicipio(Municipio municipio);

     Boolean eliminarMunicipio(Integer id);
}
