package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EstudianteHabilidadRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EstudianteHabilidadRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.EstudianteHabilidadServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteHabilidadService implements EstudianteHabilidadServiceInterface {

    private final EstudianteHabilidadRepositoryInterface repository;

    public EstudianteHabilidadService(EstudianteHabilidadRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<EstudianteHabilidad> listaEstudianteHabilidad() {
        return repository.listarEstudianteHabilidad();
    }

    public List<EstudianteHabilidad> listarPorEstudiante(Integer idEstudiante) {
        return repository.listarPorEstudiante(idEstudiante);
    }

    public EstudianteHabilidad insertarEstudianteHabilidad(EstudianteHabilidad eh) {
        return repository.insertarEstudianteHabilidad(eh);
    }

    public boolean eliminarEstudianteHabilidad(Integer idEstudiante, Integer idHabilidad) {
        return repository.eliminarEstudianteHabilidad(idEstudiante, idHabilidad);
    }
}