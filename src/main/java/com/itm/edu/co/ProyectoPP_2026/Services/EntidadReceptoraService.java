package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Repositories.EntidadReceptoraRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EntidadReceptoraRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.EntidadReceptoraServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadReceptoraService implements EntidadReceptoraServiceInterface {
    private final EntidadReceptoraRepositoryInterface repository;

    public EntidadReceptoraService(EntidadReceptoraRepositoryInterface repository) {
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
