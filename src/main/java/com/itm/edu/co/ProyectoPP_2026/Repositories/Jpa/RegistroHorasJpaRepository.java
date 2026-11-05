package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface RegistroHorasJpaRepository extends JpaRepository<RegistroHoras, Long> {

    List<RegistroHoras> findByIdPostulacionOfertaOrderByFechaDesc(Long idPostulacionOferta);

    List<RegistroHoras> findByIdPostulacionOfertaInOrderByFechaDesc(Collection<Long> idsPostulacionOferta);

    List<RegistroHoras> findByIdPostulacionOfertaAndEstadoValidacionOrderByFechaDesc(
            Long idPostulacionOferta, String estadoValidacion);

    List<RegistroHoras> findByIdPostulacionOfertaAndFechaAndEstadoValidacionNot(
            Long idPostulacionOferta, LocalDate fecha, String estadoValidacion);

    @Query("select coalesce(sum(r.horasTrabajadas), 0) from RegistroHoras r "
            + "where r.idPostulacionOferta = :idPostulacion and r.estadoValidacion in :estados")
    Double sumHorasByEstado(@Param("idPostulacion") Long idPostulacion, @Param("estados") Collection<String> estados);

    @Query("select coalesce(sum(r.horasTrabajadas), 0) from RegistroHoras r "
            + "where r.idPostulacionOferta in :idsPostulacion and r.estadoValidacion in :estados")
    Double sumHorasByPostulacionesAndEstado(
            @Param("idsPostulacion") Collection<Long> idsPostulacion, @Param("estados") Collection<String> estados);
}
