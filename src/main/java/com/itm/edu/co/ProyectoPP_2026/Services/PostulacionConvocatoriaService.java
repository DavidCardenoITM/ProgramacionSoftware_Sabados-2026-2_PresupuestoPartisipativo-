package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.PostulacionConvocatoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulacionConvocatoriaService {
    private final PostulacionConvocatoriaRepository repository;

    public PostulacionConvocatoriaService(PostulacionConvocatoriaRepository repository) {
        this.repository = repository;
    }

    public List<PostulacionConvocatoria> listaPostulacion(){
        return repository.listarPostulacion();
    }

    public PostulacionConvocatoria listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public PostulacionConvocatoria insertarPostulacion(PostulacionConvocatoria postulacion){
        return repository.insertarPostulacion(postulacion);
    }

    public PostulacionConvocatoria actualizarPostulacion(PostulacionConvocatoria postulacion){
        return repository.actualizarPostulacion(postulacion);
    }

    public Boolean eliminarPostulacion(Integer id){
        return repository.eliminarPostulacion(id);
    }

}
