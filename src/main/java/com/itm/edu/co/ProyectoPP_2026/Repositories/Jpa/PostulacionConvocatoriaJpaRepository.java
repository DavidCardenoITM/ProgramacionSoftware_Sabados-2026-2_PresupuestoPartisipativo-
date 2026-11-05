package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostulacionConvocatoriaJpaRepository extends JpaRepository<PostulacionConvocatoria, Integer> {

    boolean existsByIdEstudianteAndIdConvocatoria(Integer idEstudiante, Integer idConvocatoria);

    Optional<PostulacionConvocatoria> findByIdEstudianteAndIdConvocatoria(Integer idEstudiante, Integer idConvocatoria);

    Optional<PostulacionConvocatoria> findFirstByIdEstudianteAndEstadoOrderByFechaPostulacionDesc(
            Integer idEstudiante, String estado);

    long countByIdConvocatoriaAndEstado(Integer idConvocatoria, String estado);

    List<PostulacionConvocatoria> findByIdConvocatoriaOrderByFechaPostulacionAsc(Integer idConvocatoria);

    List<PostulacionConvocatoria> findByIdConvocatoriaAndEstadoOrderByFechaPostulacionAsc(
            Integer idConvocatoria, String estado);

    List<PostulacionConvocatoria> findByIdEstudianteOrderByFechaPostulacionDesc(Integer idEstudiante);
}
