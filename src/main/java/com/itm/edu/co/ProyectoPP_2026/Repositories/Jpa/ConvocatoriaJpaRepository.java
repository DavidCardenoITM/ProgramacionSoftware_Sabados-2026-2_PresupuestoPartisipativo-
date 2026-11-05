package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConvocatoriaJpaRepository extends JpaRepository<Convocatoria, Integer> {

    boolean existsByIdMunicipioAndNombreIgnoreCase(Integer idMunicipio, String nombre);

    List<Convocatoria> findByEstadoOrderByFechaCierreDesc(String estado);

    List<Convocatoria> findByIdMunicipioOrderByFechaCierreDesc(Integer idMunicipio);

    List<Convocatoria> findByIdMunicipioAndEstadoOrderByFechaCierreDesc(Integer idMunicipio, String estado);

    List<Convocatoria> findByEstadoAndFechaCierreLessThan(String estado, LocalDate fecha);

    List<Convocatoria> findByEstadoAndFechaAperturaLessThanEqualAndFechaCierreGreaterThanEqual(
            String estado, LocalDate desde, LocalDate hasta);

    List<Convocatoria> findByIdMunicipioAndEstadoAndFechaAperturaLessThanEqualAndFechaCierreGreaterThanEqual(
            Integer idMunicipio, String estado, LocalDate desde, LocalDate hasta);
}
