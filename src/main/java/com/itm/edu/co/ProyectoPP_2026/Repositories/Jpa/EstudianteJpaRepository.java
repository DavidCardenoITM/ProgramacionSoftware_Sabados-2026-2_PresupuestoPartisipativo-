package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteJpaRepository extends JpaRepository<Estudiante, Integer> {

    Optional<Estudiante> findByIdUsuario(Integer idUsuario);

    Optional<Estudiante> findByDocumentoIdentidad(String documentoIdentidad);

    boolean existsByDocumentoIdentidad(String documentoIdentidad);

    List<Estudiante> findByIdMunicipio(Integer idMunicipio);
}
