package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidacionFirmaJpaRepository extends JpaRepository<ValidacionFirma, Integer> {

    Optional<ValidacionFirma> findByIdRegistroHoras(Integer idRegistroHoras);

    boolean existsByIdRegistroHoras(Integer idRegistroHoras);
}
