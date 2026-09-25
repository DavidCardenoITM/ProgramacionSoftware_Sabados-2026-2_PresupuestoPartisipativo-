package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class EstudianteRepositoryDAOHelper {

    public String listarEstudiantes(){
        return "select id,id_usuario,documento_identidad,id_municipio,universidad from estudiante";
    }

    public String listarPorId(Integer id){
        return "select id,id_usuario,documento_identidad,id_municipio,universidad from estudiante where id =" + id;
    }

    public String insertarEstudiante(){
        return " insert into estudiante (id_usuario, documento_identidad, id_municipio, universidad)" +
                " values (?,?,?,?) ";
    }

    public String buscarPorDocumento(){
        return " select id,id_usuario,documento_identidad,id_municipio,universidad from estudiante" +
                " where documento_identidad = ? ";
    }

    public String actualizarEstudiante(){
        return " update estudiante set id_usuario = ?, documento_identidad = ?, id_municipio = ?, universidad = ? where id = ? ";
    }

    public String eliminarEstudiante(){
        return " delete from estudiante where id = ? ";
    }

}
