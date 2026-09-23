package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class AuditoriaRepositoryDAOHelper {

    public String listarAuditorias(){
        return "select id,id_usuario,accion,tabla_afectada,fecha,detalles from auditoria";
    }

    public String listarPorId(Integer id){
        return "select id,id_usuario,accion,tabla_afectada,fecha,detalles from auditoria where id =" + id;
    }

    public String listarPorUsuario(){
        return "select id,id_usuario,accion,tabla_afectada,fecha,detalles from auditoria where id_usuario = ?";
    }

    public String insertarAuditoria(){
        return " insert into auditoria (id_usuario, accion, tabla_afectada, fecha, detalles)" +
                " values (?,?,?,?,?) ";
    }

}