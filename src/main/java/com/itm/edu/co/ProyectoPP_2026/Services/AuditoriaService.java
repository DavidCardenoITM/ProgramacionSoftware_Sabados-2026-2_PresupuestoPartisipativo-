package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.AuditoriaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.AuditoriaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.AuditoriaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService implements AuditoriaServiceInterface {
    private final AuditoriaRepositoryInterface repository;

    public AuditoriaService(AuditoriaRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Auditoria> listaAuditoria(){
        return repository.listarAuditoria();
    }

    public Auditoria listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Auditoria insertarAuditoria(Auditoria auditoria){
        return repository.insertarAuditoria(auditoria);
    }

}