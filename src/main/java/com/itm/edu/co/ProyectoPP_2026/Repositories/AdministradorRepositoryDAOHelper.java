package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class AdministradorRepositoryDAOHelper {

    public String listarAdministradores(){
        return "select id,id_usuario from administrador";
    }

    public String listarPorId(Integer id){
        return "select id,id_usuario from administrador where id =" + id;
    }

    public String insertarAdministrador(){
        return " insert into administrador (id_usuario)" +
                " values (?) ";
    }

    public String buscarPorIdUsuario(){
        return " select id,id_usuario from administrador" +
                " where id_usuario = ? ";
    }

    public String actualizarAdministrador(){
        return " update administrador set id_usuario = ? where id = ? ";
    }

    public String eliminarAdministrador(){
        return " delete from administrador where id = ? ";
    }

}
