package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;

import java.util.List;

public interface AuditoriaServiceInterface {
     List<Auditoria> listaAuditoria();

     Auditoria listarPorId(Integer id);

     Auditoria insertarAuditoria(Auditoria auditoria);
}
