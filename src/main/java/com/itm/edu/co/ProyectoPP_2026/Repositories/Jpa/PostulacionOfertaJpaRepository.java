package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostulacionOfertaJpaRepository extends JpaRepository<PostulacionOferta, Long> {

    boolean existsByIdEstudianteAndIdOferta(Long idEstudiante, Long idOferta);

    Optional<PostulacionOferta> findByIdEstudianteAndIdOferta(Long idEstudiante, Long idOferta);

    Optional<PostulacionOferta> findByIdAndIdOferta(Long id, Long idOferta);

    List<PostulacionOferta> findByIdEstudianteOrderByFechaPostulacionDesc(Long idEstudiante);

    List<PostulacionOferta> findByIdOfertaOrderByFechaPostulacionAsc(Long idOferta);

    List<PostulacionOferta> findByIdOfertaAndEstadoOrderByFechaPostulacionAsc(Long idOferta, String estado);

    List<PostulacionOferta> findByIdOfertaAndEstadoInOrderByFechaPostulacionAsc(Long idOferta, Collection<String> estados);

    List<PostulacionOferta> findByIdOfertaInAndEstadoInOrderByFechaPostulacionAsc(
            Collection<Long> idsOferta, Collection<String> estados);

    long countByIdOfertaAndEstado(Long idOferta, String estado);

    long countByIdEstudianteAndEstadoIn(Long idEstudiante, Collection<String> estados);
}
