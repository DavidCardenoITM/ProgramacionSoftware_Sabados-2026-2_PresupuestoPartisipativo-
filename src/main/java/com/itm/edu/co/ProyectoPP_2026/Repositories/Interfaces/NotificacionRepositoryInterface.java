package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface NotificacionRepositoryInterface {

     List<Notificacion> listarNotificacion();

     Notificacion listarPorId(Integer id);

     Notificacion insertarNotificacion(Notificacion notificacion);

     Notificacion actualizarNotificacion(Notificacion notificacion);

     Boolean marcarLeido(Integer id, Boolean leido);

     Boolean eliminarNotificacion(Integer id);
}
