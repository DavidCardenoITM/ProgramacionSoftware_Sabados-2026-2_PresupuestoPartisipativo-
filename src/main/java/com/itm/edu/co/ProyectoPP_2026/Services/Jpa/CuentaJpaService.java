package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CuentaDtos.CuentaResumen;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.UsuarioJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.CuentaJpaServiceInterface;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class CuentaJpaService implements CuentaJpaServiceInterface {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("ACTIVO", "INACTIVO", "SUSPENDIDO", "PENDIENTE");
    private static final Set<String> ROLES_VALIDOS = Set.of("ADMINISTRADOR", "ESTUDIANTE", "REPRESENTANTE");

    private final UsuarioJpaRepository usuarioRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;

    public CuentaJpaService(UsuarioJpaRepository usuarioRepository,
                            AutorizacionJpaService autorizacion,
                            AuditoriaJpaService auditoria) {
        this.usuarioRepository = usuarioRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResumen> listar(String rol, String estado, String texto) {
        Specification<Usuarios> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (rol != null && !rol.isBlank()) {
                predicates.add(builder.equal(builder.upper(root.get("rol")), rol.trim().toUpperCase(Locale.ROOT)));
            }
            if (estado != null && !estado.isBlank()) {
                predicates.add(builder.equal(builder.upper(root.get("estado")),
                        estado.trim().toUpperCase(Locale.ROOT)));
            }
            if (texto != null && !texto.isBlank()) {
                String patron = "%" + texto.trim().toLowerCase(Locale.ROOT) + "%";
                predicates.add(builder.or(
                        builder.like(builder.lower(root.get("nombres")), patron),
                        builder.like(builder.lower(root.get("apellidos")), patron),
                        builder.like(builder.lower(root.get("correo")), patron)));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return usuarioRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "nombres"))
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResumen obtener(Integer id) {
        return mapear(buscar(id));
    }

    @Override
    @Transactional
    public CuentaResumen cambiarEstado(Integer id, String estado, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        String nuevoEstado = estado == null ? "" : estado.trim().toUpperCase(Locale.ROOT);
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ESTADO_INVALIDO",
                    "El estado debe ser uno de: ACTIVO, INACTIVO, SUSPENDIDO o PENDIENTE");
        }
        Usuarios usuario = buscar(id);
        usuario.setEstado(nuevoEstado);
        usuarioRepository.save(usuario);
        auditoria.registrar(idUsuario, "CAMBIAR_ESTADO_CUENTA", "usuario",
                "Usuario " + id + " cambio a estado " + nuevoEstado);
        return mapear(usuario);
    }

    @Override
    @Transactional
    public CuentaResumen cambiarRol(Integer id, String rol, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        String nuevoRol = rol == null ? "" : rol.trim().toUpperCase(Locale.ROOT);
        if (!ROLES_VALIDOS.contains(nuevoRol)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ROL_INVALIDO",
                    "El rol debe ser uno de: ADMINISTRADOR, ESTUDIANTE o REPRESENTANTE");
        }
        Usuarios usuario = buscar(id);
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);
        auditoria.registrar(idUsuario, "CAMBIAR_ROL_CUENTA", "usuario",
                "Usuario " + id + " cambio a rol " + nuevoRol);
        return mapear(usuario);
    }

    private Usuarios buscar(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CUENTA_INVALIDA", "El id de la cuenta no es valido");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "CUENTA_NO_ENCONTRADA",
                        "La cuenta indicada no existe"));
    }

    private CuentaResumen mapear(Usuarios usuario) {
        return new CuentaResumen(usuario.getId(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getCorreo(), usuario.getRol(), usuario.getEstado(), usuario.getFechaRegistro());
    }
}
