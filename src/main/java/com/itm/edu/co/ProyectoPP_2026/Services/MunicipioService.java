package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Repositories.MunicipioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {
    private final MunicipioRepository repository;

    public MunicipioService(MunicipioRepository repository) {
        this.repository = repository;
    }

    public List<Municipio> listaMunicipio(){
        return repository.listarMunicipio();
    }

    public Municipio listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Municipio insertarMunicipio(Municipio municipio){
        return repository.insertarMunicipio(municipio);
    }

    public Municipio actualizarMunicipio(Municipio municipio){
        return repository.actualizarMunicipio(municipio);
    }

    public Boolean eliminarMunicipio(Integer id){
        return repository.eliminarMunicipio(id);
    }

}