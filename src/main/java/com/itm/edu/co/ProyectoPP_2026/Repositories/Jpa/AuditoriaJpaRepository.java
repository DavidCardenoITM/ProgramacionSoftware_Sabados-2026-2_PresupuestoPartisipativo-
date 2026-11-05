package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaJpaRepository extends JpaRepository<Auditoria, Integer> {

    List<Auditoria> findAllByOrderByFechaDesc();

    List<Auditoria> findByIdUsuarioOrderByFechaDesc(Integer idUsuario);
}
