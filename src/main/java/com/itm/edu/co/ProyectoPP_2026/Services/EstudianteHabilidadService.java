package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EstudianteHabilidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteHabilidadService {

    private final EstudianteHabilidadRepository repository;

    public EstudianteHabilidadService(EstudianteHabilidadRepository repository) {
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