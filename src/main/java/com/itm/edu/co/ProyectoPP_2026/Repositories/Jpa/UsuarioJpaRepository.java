package com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<Usuarios, Integer>, JpaSpecificationExecutor<Usuarios> {

    Optional<Usuarios> findByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Usuarios> findByEstadoOrderByNombresAsc(String estado);

    List<Usuarios> findByRolOrderByNombresAsc(String rol);

    List<Usuarios> findByEstadoAndRolOrderByNombresAsc(String estado, String rol);

    List<Usuarios> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrCorreoContainingIgnoreCaseOrderByNombresAsc(
            String nombres, String apellidos, String correo);

    long countByRolAndEstado(String rol, String estado);
}
