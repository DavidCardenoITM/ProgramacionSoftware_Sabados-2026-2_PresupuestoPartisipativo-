package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EntidadReceptoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadReceptoraService {
    private final EntidadReceptoraRepository repository;

    public EntidadReceptoraService(EntidadReceptoraRepository repository) {
        this.repository = repository;
    }

    public List<EntidadReceptora> listaEntidad(){
        return repository.listarEntidad();
    }

    public EntidadReceptora listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public EntidadReceptora insertarEntidad(EntidadReceptora entidad){
        return repository.insertarEntidad(entidad);
    }

    public EntidadReceptora actualizarEntidad(EntidadReceptora entidad){
        return repository.actualizarEntidad(entidad);
    }

    public Boolean eliminarEntidad(Integer id){
        return repository.eliminarEntidad(id);
    }

}
