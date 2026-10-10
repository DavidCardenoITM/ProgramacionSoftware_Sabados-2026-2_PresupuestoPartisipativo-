package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Repositories.HojaDeVidaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.HojaDeVidaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.HojaDeVidaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HojaDeVidaService implements HojaDeVidaServiceInterface {

    private final HojaDeVidaRepositoryInterface repository;

    public HojaDeVidaService(HojaDeVidaRepositoryInterface repository) {
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