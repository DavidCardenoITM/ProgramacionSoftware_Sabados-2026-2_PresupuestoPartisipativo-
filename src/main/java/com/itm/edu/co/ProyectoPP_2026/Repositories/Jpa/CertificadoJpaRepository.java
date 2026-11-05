package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificadoJpaRepository extends JpaRepository<Certificado, Long> {

    Optional<Certificado> findByIdEstudiante(Long idEstudiante);

    boolean existsByIdEstudiante(Long idEstudiante);

    Optional<Certificado> findByCodigoVerificacionIgnoreCase(String codigoVerificacion);
}
