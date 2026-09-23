package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Repositories.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {
    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public List<Notificacion> listaNotificacion(){
        return repository.listarNotificacion();
    }

    public Notificacion listarPorId(Integer id) {
        return repository.listarPorId(id);
    }


    public Notificacion insertarNotificacion(Notificacion notificacion){
        return repository.insertarNotificacion(notificacion);
    }

    public Notificacion actualizarNotificacion(Notificacion notificacion){
        return repository.actualizarNotificacion(notificacion);
    }

    public Boolean marcarLeido(Integer id, Boolean leido){
        return repository.marcarLeido(id, leido);
    }

    public Boolean eliminarNotificacion(Integer id){
        return repository.eliminarNotificacion(id);
    }

}