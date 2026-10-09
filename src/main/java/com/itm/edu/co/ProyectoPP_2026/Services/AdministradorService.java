package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Interfaces.IAdministradorService;
import com.itm.edu.co.ProyectoPP_2026.Repositories.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService implements IAdministradorService {

    private final AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Administrador> listaAdministrador() {
        return repository.listarAdministrador();
    }

    @Override
    public Administrador listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    @Override
    public Administrador insertarAdministrador(Administrador administrador) {
        return repository.insertarAdministrador(administrador);
    }

    @Override
    public Administrador actualizarAdministrador(Administrador administrador) {
        return repository.actualizarAdministrador(administrador);
    }

    @Override
    public Boolean eliminarAdministrador(Integer id) {
        return repository.eliminarAdministrador(id);
    }
}