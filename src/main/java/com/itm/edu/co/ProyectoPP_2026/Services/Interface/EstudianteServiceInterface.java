package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;

import java.util.List;

public interface EstudianteServiceInterface {

     List<Estudiante> listaEstudiante();

     Estudiante listarPorId(Integer id);

     Estudiante insertarEstudiante(Estudiante estudiante);
     Estudiante actualizarEstudiante(Estudiante estudiante);

     Boolean eliminarEstudiante(Integer id);
}
