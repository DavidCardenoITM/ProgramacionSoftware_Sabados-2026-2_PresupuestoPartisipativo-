package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class NotificacionRepositoryDAOHelper {

    public String listarNotificaciones(){
        return "select id,id_usuario,tipo,mensaje,fecha,leido from notificacion";
    }

    public String listarPorId(Integer id){
        return "select id,id_usuario,tipo,mensaje,fecha,leido from notificacion where id =" + id;
    }

    public String listarPorUsuario(){
        return "select id,id_usuario,tipo,mensaje,fecha,leido from notificacion where id_usuario = ?";
    }

    public String insertarNotificacion(){
        return " insert into notificacion (id_usuario, tipo, mensaje, fecha, leido)" +
                " values (?,?,?,?,?) ";
    }

    public String actualizarNotificacion(){
        return " update notificacion set id_usuario = ?, tipo = ?, mensaje = ?, fecha = ?, leido = ?" +
                " where id = ? ";
    }

    public String marcarLeido(){
        return " update notificacion set leido = ? where id = ? ";
    }

    public String eliminarNotificacion(){
        return " delete from notificacion where id = ? ";
    }

}