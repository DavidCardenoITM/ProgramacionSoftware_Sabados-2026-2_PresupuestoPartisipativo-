package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class ConvocatoriaRepositoryDAOHelper {

    public String listarConvocatorias(){
        return "select id,id_municipio,nombre,fecha_apertura,fecha_cierre,cupos,estado from convocatoria";
    }

    public String listarPorId(Integer id){
        return "select id,id_municipio,nombre,fecha_apertura,fecha_cierre,cupos,estado from convocatoria where id =" + id;
    }

    public String insertarConvocatoria(){
        return " insert into convocatoria (id_municipio, nombre, fecha_apertura, fecha_cierre, cupos, estado)" +
                " values (?,?,?,?,?,?) ";
    }

    public String buscarPorNombre(){
        return " select id,id_municipio,nombre,fecha_apertura,fecha_cierre,cupos,estado from convocatoria" +
                " where nombre = ? ";
    }

    public String actualizarConvocatoria(){
        return " update convocatoria set id_municipio = ?, nombre = ?, fecha_apertura = ?, fecha_cierre = ?, cupos = ?, estado = ? where id = ? ";
    }

    public String eliminarConvocatoria(){
        return " delete from convocatoria where id = ? ";
    }

}
