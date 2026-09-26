package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class EntidadReceptoraRepositoryDAOHelper {

    public String listarEntidades(){
        return "select id,nombre,nit,sector,direccion,id_municipio,estado_validacion from entidad_receptora";
    }

    public String listarPorId(Integer id){
        return "select id,nombre,nit,sector,direccion,id_municipio,estado_validacion from entidad_receptora where id =" + id;
    }

    public String insertarEntidad(){
        return " insert into entidad_receptora (nombre, nit, sector, direccion, id_municipio, estado_validacion)" +
                " values (?,?,?,?,?,?) ";
    }

    public String buscarPorNit(){
        return " select id,nombre,nit,sector,direccion,id_municipio,estado_validacion from entidad_receptora" +
                " where nit = ? ";
    }

    public String actualizarEntidad(){
        return " update entidad_receptora set nombre = ?, nit = ?, sector = ?, direccion = ?, id_municipio = ?, estado_validacion = ? where id = ? ";
    }

    public String eliminarEntidad(){
        return " delete from entidad_receptora where id = ? ";
    }

}
