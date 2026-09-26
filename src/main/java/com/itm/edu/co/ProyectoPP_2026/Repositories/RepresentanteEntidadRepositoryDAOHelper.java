package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class RepresentanteEntidadRepositoryDAOHelper {

    public String listarRepresentantes(){
        return "select id,id_usuario,id_entidad,cargo from representante_entidad";
    }

    public String listarPorId(Integer id){
        return "select id,id_usuario,id_entidad,cargo from representante_entidad where id =" + id;
    }

    public String insertarRepresentante(){
        return " insert into representante_entidad (id_usuario, id_entidad, cargo)" +
                " values (?,?,?) ";
    }

    public String buscarPorIdUsuario(){
        return " select id,id_usuario,id_entidad,cargo from representante_entidad" +
                " where id_usuario = ? ";
    }

    public String actualizarRepresentante(){
        return " update representante_entidad set id_usuario = ?, id_entidad = ?, cargo = ? where id = ? ";
    }

    public String eliminarRepresentante(){
        return " delete from representante_entidad where id = ? ";
    }

}
