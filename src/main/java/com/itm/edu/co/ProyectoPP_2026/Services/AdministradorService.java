package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Repositories.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {
    private final AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    public List<Administrador> listaAdministrador(){
        return repository.listarAdministrador();
    }

    public Administrador listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Administrador insertarAdministrador(Administrador administrador){
        return repository.insertarAdministrador(administrador);
    }

    public Administrador actualizarAdministrador(Administrador administrador){
        return repository.actualizarAdministrador(administrador);
    }

    public Boolean eliminarAdministrador(Integer id){
        return repository.eliminarAdministrador(id);
    }

}
