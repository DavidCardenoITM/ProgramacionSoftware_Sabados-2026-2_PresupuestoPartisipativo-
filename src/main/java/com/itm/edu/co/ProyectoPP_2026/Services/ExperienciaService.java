package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
import com.itm.edu.co.ProyectoPP_2026.Repositories.ExperienciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienciaService {

    private final ExperienciaRepository repository;

    public ExperienciaService(ExperienciaRepository repository) {
        this.repository = repository;
    }

    public List<Experiencia> listaExperiencia() {
        return repository.listarExperiencia();
    }

    public Experiencia listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public List<Experiencia> listarPorHojaDeVida(Integer idHojaDeVida) {
        return repository.listarPorHojaDeVida(idHojaDeVida);
    }

    public Experiencia insertarExperiencia(Experiencia experiencia) {
        return repository.insertarExperiencia(experiencia);
    }
}
