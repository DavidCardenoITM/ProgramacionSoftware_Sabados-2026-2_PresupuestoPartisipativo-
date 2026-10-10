package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;

import java.util.List;

public interface RegistroHorasServiceInterface {

    List<RegistroHoras> listarRegistros();

    RegistroHoras buscarPorId(Long id);

    RegistroHoras insertarRegistro(RegistroHoras registro);

    RegistroHoras actualizarRegistro(RegistroHoras registro);

    Boolean eliminarRegistro(Long id);
}
