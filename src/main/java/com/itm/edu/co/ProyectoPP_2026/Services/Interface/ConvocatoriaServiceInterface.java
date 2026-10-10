package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;

import java.util.List;

public interface ConvocatoriaServiceInterface {

     List<Convocatoria> listaConvocatoria();

     Convocatoria listarPorId(Integer id);

     Convocatoria insertarConvocatoria(Convocatoria convocatoria);

     Convocatoria actualizarConvocatoria(Convocatoria convocatoria);

     Boolean eliminarConvocatoria(Integer id);
}
