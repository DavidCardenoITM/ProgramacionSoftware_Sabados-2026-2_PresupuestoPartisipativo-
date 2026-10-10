package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.ConvocatoriaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.ConvocatoriaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.ConvocatoriaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocatoriaService implements ConvocatoriaServiceInterface {
    private final ConvocatoriaRepositoryInterface repository;

    public ConvocatoriaService(ConvocatoriaRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Convocatoria> listaConvocatoria(){
        return repository.listarConvocatoria();
    }

    public Convocatoria listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Convocatoria insertarConvocatoria(Convocatoria convocatoria){
        return repository.insertarConvocatoria(convocatoria);
    }

    public Convocatoria actualizarConvocatoria(Convocatoria convocatoria){
        return repository.actualizarConvocatoria(convocatoria);
    }

    public Boolean eliminarConvocatoria(Integer id){
        return repository.eliminarConvocatoria(id);
    }

}
