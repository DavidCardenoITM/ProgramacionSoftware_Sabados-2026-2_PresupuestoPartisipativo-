package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;

import java.util.List;

public interface EstudianteHabilidadServiceInterface {
     List<EstudianteHabilidad> listaEstudianteHabilidad();

     List<EstudianteHabilidad> listarPorEstudiante(Integer idEstudiante);

     EstudianteHabilidad insertarEstudianteHabilidad(EstudianteHabilidad eh);

     boolean eliminarEstudianteHabilidad(Integer idEstudiante, Integer idHabilidad);
}
