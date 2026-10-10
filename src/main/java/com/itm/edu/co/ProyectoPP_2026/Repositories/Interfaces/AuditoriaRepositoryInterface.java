package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;

import java.util.List;

public interface AuditoriaRepositoryInterface {

    List<Auditoria> listarAuditoria();
    Auditoria listarPorId(Integer id);
    Auditoria insertarAuditoria(Auditoria auditoria);
}
