package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.AuditoriaJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuditoriaJpaService {

    private final AuditoriaJpaRepository repository;

    public AuditoriaJpaService(AuditoriaJpaRepository repository) {
        this.repository = repository;
    }

    public void registrar(Integer idUsuario, String accion, String tablaAfectada, String detalles) {
        Auditoria auditoria = new Auditoria();
        auditoria.setIdUsuario(idUsuario == null || idUsuario <= 0 ? 1 : idUsuario);
        auditoria.setAccion(accion);
        auditoria.setTablaAfectada(tablaAfectada);
        auditoria.setFecha(LocalDate.now());
        auditoria.setDetalles(detalles);
        repository.save(auditoria);
    }
}
