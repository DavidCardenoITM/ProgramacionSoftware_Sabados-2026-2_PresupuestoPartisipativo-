package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Repositories.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {
    private final UsuariosRepository repository;

    public UsuariosService(UsuariosRepository repository) {
        this.repository = repository;
    }

    public List<Usuarios> listaUsuario(){
        return repository.listarUsuario();
    }

    public Usuarios listarPorId(Integer id) {
        return repository.listarPorId(id);
    }

    public Usuarios insertarUsuario(Usuarios usuario){
        return repository.insertarUsuario(usuario);
    }

    public Usuarios actualizarUsuario(Usuarios usuario){
        return repository.actualizarUsuario(usuario);
    }

    public Usuarios corregirCorreo(String correo, String nuevaCorreo){
        return repository.corregirCorreo(correo, nuevaCorreo);
    }

    public Boolean eliminarUsuario(Integer id){
        return  repository.eliminarUsuario(id);
    }

}
