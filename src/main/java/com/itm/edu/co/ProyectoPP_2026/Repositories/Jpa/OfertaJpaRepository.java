package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfertaJpaRepository extends JpaRepository<OfertaLaborSocial, Integer>, JpaSpecificationExecutor<OfertaLaborSocial> {

    List<OfertaLaborSocial> findByIdEntidadOrderByFechaInicioDesc(Integer idEntidad);

    List<OfertaLaborSocial> findByEstadoOrderByFechaInicioDesc(String estado);

    long countByIdEntidadAndEstado(Integer idEntidad, String estado);

    @Modifying
    @Query("update OfertaLaborSocial o set o.cuposDisponibles = o.cuposDisponibles - 1 where o.id = :id and o.cuposDisponibles > 0")
    int descontarCupo(@Param("id") Integer id);

    @Modifying
    @Query("update OfertaLaborSocial o set o.cuposDisponibles = o.cuposDisponibles + 1 where o.id = :id")
    int devolverCupo(@Param("id") Integer id);
}
