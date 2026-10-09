package com.itm.edu.co.ProyectoPP_2026.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import java.util.List;

public interface IUsuariosService {
    List<Usuarios> listaUsuario();
    Usuarios listarPorId(Integer id);
    Usuarios insertarUsuario(Usuarios usuario);
    Usuarios actualizarUsuario(Usuarios usuario);
    Usuarios corregirCorreo(String correo, String nuevaCorreo);
    Boolean eliminarUsuario(Integer id);
}