package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<Estudiante> listaEstudiante(){
        return repository.listarEstudiante();
    }

    public Estudiante listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Estudiante insertarEstudiante(Estudiante estudiante){
        return repository.insertarEstudiante(estudiante);
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante){
        return repository.actualizarEstudiante(estudiante);
    }

    public Boolean eliminarEstudiante(Integer id){
        return repository.eliminarEstudiante(id);
    }

}
