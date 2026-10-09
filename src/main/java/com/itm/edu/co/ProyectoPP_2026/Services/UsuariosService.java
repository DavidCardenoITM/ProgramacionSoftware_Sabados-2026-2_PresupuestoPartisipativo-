package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Interfaces.IUsuariosService;
import com.itm.edu.co.ProyectoPP_2026.Repositories.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    private final UsuariosRepository repository;

    public UsuariosService(UsuariosRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Usuarios> listaUsuario() {
        return repository.listarUsuario();
    }

    @Override
    public Usuarios listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    @Override
    public Usuarios insertarUsuario(Usuarios usuario) {
        return repository.insertarUsuario(usuario);
    }

    @Override
    public Usuarios actualizarUsuario(Usuarios usuario) {
        return repository.actualizarUsuario(usuario);
    }

    @Override
    public Usuarios corregirCorreo(String correo, String nuevaCorreo) {
        return repository.corregirCorreo(correo, nuevaCorreo);
    }

    @Override
    public Boolean eliminarUsuario(Integer id) {
        return repository.eliminarUsuario(id);
    }
}