package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;

import java.util.List;

public interface ConvocatoriaRepositoryInterface {
    List<Convocatoria> listarConvocatoria();
    Convocatoria listarPorId(Integer id);
    Convocatoria insertarConvocatoria(Convocatoria convocatoria);
    Convocatoria actualizarConvocatoria(Convocatoria convocatoria);
    Boolean eliminarConvocatoria(Integer id);
}
