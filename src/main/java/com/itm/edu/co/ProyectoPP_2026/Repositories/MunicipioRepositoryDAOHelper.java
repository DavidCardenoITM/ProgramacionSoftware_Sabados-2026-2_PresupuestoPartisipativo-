package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class MunicipioRepositoryDAOHelper {

    public String listarMunicipios(){
        return "select id,nombre,codigo_dane from municipio";
    }

    public String listarPorId(Integer id){
        return "select id,nombre,codigo_dane from municipio where id =" + id;
    }

    public String insertarMunicipio(){
        return " insert into municipio (nombre, codigo_dane)" +
                " values (?,?) ";
    }

    public String buscarPorCodigoDane(){
        return " select id,nombre,codigo_dane from municipio" +
                " where codigo_dane = ? ";
    }

    public String actualizarMunicipio(){
        return " update municipio set nombre = ?, codigo_dane = ? where id = ? ";
    }

    public String eliminarMunicipio(){
        return " delete from municipio where id = ? ";
    }

}