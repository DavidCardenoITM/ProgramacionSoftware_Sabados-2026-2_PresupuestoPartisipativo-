package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EstudianteRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EstudianteRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.EstudianteServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService implements EstudianteServiceInterface {
    private final EstudianteRepositoryInterface repository;

    public EstudianteService(EstudianteRepositoryInterface repository) {
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
