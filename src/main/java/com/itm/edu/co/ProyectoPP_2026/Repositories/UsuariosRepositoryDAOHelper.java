package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class UsuariosRepositoryDAOHelper {

    public String listarUsuarios(){
        return "select id,nombres,apellidos,correo,contrasena_hash,rol,fecha_registro,estado from usuario";
    }

    public String listarPorId(Integer id){
        return "select id,nombres,apellidos,correo,contrasena_hash,rol,fecha_registro,estado from usuario where id =" + id;
    }

    public String insertarUsuario(){
        return " insert into usuario (nombres, apellidos, correo, contrasena_hash,rol,fecha_registro,estado)" +
                " values (?,?,?,?,?,?,?) ";
    }

    public String buscarPorCorreo(){
        return " select id,nombres,apellidos,correo,contrasena_hash,rol,fecha_registro,estado from usuario" +
                " where correo = ? ";
    }

    public String actualizarUsuario(){
        return " update usuario set nombres = ?, apellidos = ?, correo = ?, contrasena_hash = ?," +
                "rol = ?, fecha_registro = ?, estado = ?  where id = ? ";
    }

    public String corregirCorreo(){
        return " update usuario set correo = ? where correo = ? ";
    }

    public String eliminarUsuario(){
        return " delete from usuario where id = ? ";
    }

}
