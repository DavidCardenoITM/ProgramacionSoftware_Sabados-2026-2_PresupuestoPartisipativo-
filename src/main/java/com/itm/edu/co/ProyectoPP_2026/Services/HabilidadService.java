package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.HabilidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabilidadService {

    private final HabilidadRepository repository;

    public HabilidadService(HabilidadRepository repository) {
        this.repository = repository;
    }

    public List<Habilidad> listaHabilidad() {
        return repository.listarHabilidad();
    }

    public Habilidad listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Habilidad insertarHabilidad(Habilidad habilidad) {
        return repository.insertarHabilidad(habilidad);
    }
}
