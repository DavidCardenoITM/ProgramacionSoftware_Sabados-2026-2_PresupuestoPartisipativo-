package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.MunicipioRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Repositories.MunicipioRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.MunicipioServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService implements MunicipioServiceInterface{
    private final MunicipioRepositoryInterface repository;

    public MunicipioService(MunicipioRepositoryInterface repository) {
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