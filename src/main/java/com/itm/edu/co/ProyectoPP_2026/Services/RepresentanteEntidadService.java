package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.RepresentanteEntidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentanteEntidadService {
    private final RepresentanteEntidadRepository repository;

    public RepresentanteEntidadService(RepresentanteEntidadRepository repository) {
        this.repository = repository;
    }

    public List<RepresentanteEntidad> listaRepresentante(){
        return repository.listarRepresentante();
    }

    public RepresentanteEntidad listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public RepresentanteEntidad insertarRepresentante(RepresentanteEntidad representante){
        return repository.insertarRepresentante(representante);
    }

    public RepresentanteEntidad actualizarRepresentante(RepresentanteEntidad representante){
        return repository.actualizarRepresentante(representante);
    }

    public Boolean eliminarRepresentante(Integer id){
        return repository.eliminarRepresentante(id);
    }

}
