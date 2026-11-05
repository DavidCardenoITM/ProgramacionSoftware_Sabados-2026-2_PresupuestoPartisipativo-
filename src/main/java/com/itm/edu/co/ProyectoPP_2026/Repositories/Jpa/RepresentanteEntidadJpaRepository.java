package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepresentanteEntidadJpaRepository extends JpaRepository<RepresentanteEntidad, Integer> {

    Optional<RepresentanteEntidad> findByIdUsuario(Integer idUsuario);

    List<RepresentanteEntidad> findByIdEntidad(Integer idEntidad);
}
