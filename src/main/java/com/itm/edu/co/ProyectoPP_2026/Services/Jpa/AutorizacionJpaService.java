package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.EstudianteJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.RepresentanteEntidadJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.UsuarioJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorizacionJpaService {

    private final UsuarioJpaRepository usuarioRepository;
    private final EstudianteJpaRepository estudianteRepository;
    private final RepresentanteEntidadJpaRepository representanteRepository;

    public AutorizacionJpaService(UsuarioJpaRepository usuarioRepository,
                                  EstudianteJpaRepository estudianteRepository,
                                  RepresentanteEntidadJpaRepository representanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
        this.representanteRepository = representanteRepository;
    }

    public Usuarios obtenerUsuario(Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "USUARIO_INVALIDO", "El usuario indicado no es valido");
        }
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "USUARIO_NO_ENCONTRADO",
                        "El usuario indicado no existe"));
    }

    public boolean esAdministrador(Usuarios usuario) {
        return usuario.getRol() != null && usuario.getRol().equalsIgnoreCase("ADMINISTRADOR");
    }

    public void validarAdministrador(Integer idUsuario) {
        Usuarios usuario = obtenerUsuario(idUsuario);
        if (!esAdministrador(usuario)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                    "La operacion requiere rol de administrador");
        }
    }

    public Estudiante obtenerEstudiante(Long idEstudiante) {
        if (idEstudiante == null || idEstudiante <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ESTUDIANTE_INVALIDO",
                    "El estudiante indicado no es valido");
        }
        return estudianteRepository.findById(idEstudiante.intValue())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "ESTUDIANTE_NO_ENCONTRADO",
                        "El estudiante indicado no existe"));
    }

    public void validarEstudiante(Integer idUsuario, Long idEstudiante) {
        Estudiante estudiante = obtenerEstudiante(idEstudiante);
        Usuarios usuario = obtenerUsuario(idUsuario);
        if (esAdministrador(usuario)) {
            return;
        }
        if (!estudiante.getIdUsuario().equals(idUsuario)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                    "El usuario no puede operar sobre otro estudiante");
        }
    }

    public void validarRepresentanteDeEntidad(Integer idUsuario, Integer idEntidad) {
        if (idEntidad == null || idEntidad <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ENTIDAD_INVALIDA", "La entidad indicada no es valida");
        }
        Usuarios usuario = obtenerUsuario(idUsuario);
        if (esAdministrador(usuario)) {
            return;
        }
        RepresentanteEntidad representante = representanteRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                        "El usuario no corresponde a un representante de entidad"));
        if (!representante.getIdEntidad().equals(idEntidad)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                    "El representante no puede operar sobre otra entidad");
        }
    }

    public RepresentanteEntidad obtenerRepresentanteDeEntidad(Integer idEntidad) {
        List<RepresentanteEntidad> representantes = representanteRepository.findByIdEntidad(idEntidad);
        if (representantes.isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT, "ENTIDAD_SIN_REPRESENTANTE",
                    "La entidad no tiene un representante registrado");
        }
        return representantes.get(0);
    }

    public RepresentanteEntidad obtenerRepresentanteQueValida(Integer idUsuario, Integer idEntidad) {
        Usuarios usuario = obtenerUsuario(idUsuario);
        if (esAdministrador(usuario)) {
            return obtenerRepresentanteDeEntidad(idEntidad);
        }
        RepresentanteEntidad representante = representanteRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                        "El usuario no corresponde a un representante de entidad"));
        if (!representante.getIdEntidad().equals(idEntidad)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                    "El representante no puede operar sobre otra entidad");
        }
        return representante;
    }

    public Integer idUsuarioDeEstudiante(Long idEstudiante) {
        return obtenerEstudiante(idEstudiante).getIdUsuario();
    }

    public boolean esRepresentanteDeEntidad(Integer idUsuario, Integer idEntidad) {
        return representanteRepository.findByIdUsuario(idUsuario)
                .map(representante -> representante.getIdEntidad().equals(idEntidad))
                .orElse(false);
    }
}
