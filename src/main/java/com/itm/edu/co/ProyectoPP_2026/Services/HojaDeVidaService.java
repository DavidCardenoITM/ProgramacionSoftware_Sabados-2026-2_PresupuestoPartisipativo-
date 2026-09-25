package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Repositories.HojaDeVidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HojaDeVidaService {

    private final HojaDeVidaRepository repository;

    public HojaDeVidaService(HojaDeVidaRepository repository) {
        this.repository = repository;
    }

    public List<HojaDeVida> listaHojaDeVida() {
        return repository.listarHojaDeVida();
    }

    public HojaDeVida listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public HojaDeVida listarPorEstudiante(Integer idEstudiante) {
        return repository.listarPorEstudiante(idEstudiante);
    }

    public HojaDeVida insertarHojaDeVida(HojaDeVida hoja) {
        return repository.insertarHojaDeVida(hoja);
    }
}