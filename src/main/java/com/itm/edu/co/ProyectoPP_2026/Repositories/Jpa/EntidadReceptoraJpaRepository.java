package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntidadReceptoraJpaRepository extends JpaRepository<EntidadReceptora, Integer> {

    Optional<EntidadReceptora> findByNit(String nit);

    boolean existsByNit(String nit);

    List<EntidadReceptora> findByIdMunicipio(Integer idMunicipio);
}
