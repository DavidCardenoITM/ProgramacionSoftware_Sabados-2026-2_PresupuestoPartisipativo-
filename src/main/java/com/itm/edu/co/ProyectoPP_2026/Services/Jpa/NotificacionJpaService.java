package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.NotificacionJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionJpaService {

    private final NotificacionJpaRepository repository;

    public NotificacionJpaService(NotificacionJpaRepository repository) {
        this.repository = repository;
    }

    public void notificar(Integer idUsuario, String tipo, String mensaje) {
        if (idUsuario == null || idUsuario <= 0) {
            return;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuario(idUsuario);
        notificacion.setTipo(tipo);
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(LocalDate.now());
        notificacion.setLeido(false);
        repository.save(notificacion);
    }

    public List<Notificacion> listar(Integer idUsuario) {
        return repository.findByIdUsuarioOrderByFechaDesc(idUsuario);
    }

    public long contarNoLeidas(Integer idUsuario) {
        return repository.countByIdUsuarioAndLeidoFalse(idUsuario);
    }
}
