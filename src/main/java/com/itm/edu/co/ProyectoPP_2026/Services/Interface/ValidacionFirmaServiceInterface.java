package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.ValidacionFirmaRepositoryInterface;

import java.util.List;

public interface ValidacionFirmaServiceInterface {

     List<ValidacionFirma> listaValidacionFirma();

     ValidacionFirma listarPorId(Integer id);

     ValidacionFirma insertarValidacion(ValidacionFirma validacion) ;
}
