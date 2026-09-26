package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Repositories.ValidacionFirmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidacionFirmaService {

    private final ValidacionFirmaRepository repository;

    public ValidacionFirmaService(ValidacionFirmaRepository repository) {
        this.repository = repository;
    }

    public List<ValidacionFirma> listaValidacionFirma() {
        return repository.listarValidacionFirma();
    }

    public ValidacionFirma listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public ValidacionFirma insertarValidacion(ValidacionFirma validacion) {
        return repository.insertarValidacion(validacion);
    }
}