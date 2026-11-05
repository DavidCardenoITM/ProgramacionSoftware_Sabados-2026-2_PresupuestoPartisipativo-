package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MunicipioJpaRepository extends JpaRepository<Municipio, Integer> {

    List<Municipio> findAllByOrderByNombreAsc();

    Optional<Municipio> findByCodigoDane(String codigoDane);

    boolean existsByCodigoDane(String codigoDane);
}
