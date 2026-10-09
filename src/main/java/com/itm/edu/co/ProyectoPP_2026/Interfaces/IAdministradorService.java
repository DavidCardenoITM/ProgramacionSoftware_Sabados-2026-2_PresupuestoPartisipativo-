package com.itm.edu.co.ProyectoPP_2026.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import java.util.List;

public interface IAdministradorService {
    List<Administrador> listaAdministrador();
    Administrador listarPorId(Integer id);
    Administrador insertarAdministrador(Administrador administrador);
    Administrador actualizarAdministrador(Administrador administrador);
    Boolean eliminarAdministrador(Integer id);
}