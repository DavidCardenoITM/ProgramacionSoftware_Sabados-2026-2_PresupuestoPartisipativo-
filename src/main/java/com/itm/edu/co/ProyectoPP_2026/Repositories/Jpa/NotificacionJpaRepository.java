package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificacionJpaRepository extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByIdUsuarioOrderByFechaDesc(Integer idUsuario);

    long countByIdUsuarioAndLeidoFalse(Integer idUsuario);

    @Modifying
    @Query("update Notificacion n set n.leido = true where n.idUsuario = :idUsuario and n.leido = false")
    int marcarLeidas(@Param("idUsuario") Integer idUsuario);
}
