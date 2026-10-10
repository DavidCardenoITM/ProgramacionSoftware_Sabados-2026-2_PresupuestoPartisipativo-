package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;

import java.util.List;

public interface NotificacionServiceInterface {

     List<Notificacion> listaNotificacion();

     Notificacion listarPorId(Integer id);

     Notificacion insertarNotificacion(Notificacion notificacion);

     Notificacion actualizarNotificacion(Notificacion notificacion);

     Boolean marcarLeido(Integer id, Boolean leido);

     Boolean eliminarNotificacion(Integer id);
}
