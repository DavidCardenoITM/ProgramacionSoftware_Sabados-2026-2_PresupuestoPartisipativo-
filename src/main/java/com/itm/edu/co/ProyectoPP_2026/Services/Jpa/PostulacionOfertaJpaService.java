package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.DecisionOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostulacionOfertaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.PostularOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.OfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionConvocatoriaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionOfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.PostulacionOfertaJpaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class PostulacionOfertaJpaService implements PostulacionOfertaJpaServiceInterface {

    private final PostulacionOfertaJpaRepository postulacionRepository;
    private final PostulacionConvocatoriaJpaRepository postulacionConvocatoriaRepository;
    private final OfertaJpaRepository ofertaRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;
    private final NotificacionJpaService notificacion;

    public PostulacionOfertaJpaService(PostulacionOfertaJpaRepository postulacionRepository,
                                       PostulacionConvocatoriaJpaRepository postulacionConvocatoriaRepository,
                                       OfertaJpaRepository ofertaRepository,
                                       AutorizacionJpaService autorizacion,
                                       AuditoriaJpaService auditoria,
                                       NotificacionJpaService notificacion) {
        this.postulacionRepository = postulacionRepository;
        this.postulacionConvocatoriaRepository = postulacionConvocatoriaRepository;
        this.ofertaRepository = ofertaRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
        this.notificacion = notificacion;
    }

    @Override
    @Transactional
    public PostulacionOfertaResumen postular(Long idOferta, PostularOfertaRequest request, Integer idUsuario) {
        if (request == null || request.idEstudiante() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idEstudiante' es obligatorio");
        }
        Long idEstudiante = request.idEstudiante();
        autorizacion.validarEstudiante(idUsuario, idEstudiante);
        OfertaLaborSocial oferta = buscarOferta(idOferta);
        if (!"ACTIVA".equalsIgnoreCase(oferta.getEstado())
                && !"PUBLICADA".equalsIgnoreCase(oferta.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_NO_DISPONIBLE",
                    "La oferta no esta disponible para recibir postulaciones");
        }
        validarVigencia(oferta);
        boolean becaAprobada = postulacionConvocatoriaRepository
                .findFirstByIdEstudianteAndEstadoOrderByFechaPostulacionDesc(idEstudiante.intValue(), "ACEPTADA")
                .isPresent()
                || postulacionConvocatoriaRepository
                .findFirstByIdEstudianteAndEstadoOrderByFechaPostulacionDesc(idEstudiante.intValue(), "APROBADA")
                .isPresent();
        if (!becaAprobada) {
            throw new ApiException(HttpStatus.CONFLICT, "SIN_BECA_APROBADA",
                    "El estudiante no tiene una beca aprobada en una convocatoria");
        }
        if (postulacionRepository.existsByIdEstudianteAndIdOferta(idEstudiante, idOferta)) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_DUPLICADA",
                    "El estudiante ya tiene una postulacion en esta oferta");
        }
        PostulacionOferta postulacion = new PostulacionOferta();
        postulacion.setIdEstudiante(idEstudiante);
        postulacion.setIdOferta(idOferta);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setEstado("PENDIENTE");
        postulacionRepository.save(postulacion);
        auditoria.registrar(idUsuario, "POSTULAR_OFERTA", "postulacion_oferta",
                "Estudiante " + idEstudiante + " postulado a oferta " + idOferta);
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(idEstudiante), "POSTULACION_OFERTA",
                "Tu postulacion a la oferta '" + oferta.getTitulo() + "' fue registrada");
        return mapear(postulacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionOfertaResumen> listarPorEstudiante(Long idEstudiante, Integer idUsuario) {
        autorizacion.validarEstudiante(idUsuario, idEstudiante);
        return postulacionRepository.findByIdEstudianteOrderByFechaPostulacionDesc(idEstudiante).stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionOfertaResumen> listarPorOferta(Long idOferta, String estado, Integer idUsuario) {
        OfertaLaborSocial oferta = buscarOferta(idOferta);
        autorizacion.validarRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad());
        List<PostulacionOferta> postulaciones;
        if (estado != null && !estado.isBlank()) {
            postulaciones = postulacionRepository.findByIdOfertaAndEstadoOrderByFechaPostulacionAsc(
                    idOferta, estado.trim().toUpperCase(Locale.ROOT));
        } else {
            postulaciones = postulacionRepository.findByIdOfertaOrderByFechaPostulacionAsc(idOferta);
        }
        return postulaciones.stream().map(this::mapear).toList();
    }

    @Override
    @Transactional
    public PostulacionOfertaResumen retirar(Long idPostulacion, Integer idUsuario) {
        PostulacionOferta postulacion = buscar(idPostulacion);
        autorizacion.validarEstudiante(idUsuario, postulacion.getIdEstudiante());
        if ("RETIRADA".equalsIgnoreCase(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_YA_RETIRADA",
                    "La postulacion ya fue retirada");
        }
        if (!"PENDIENTE".equalsIgnoreCase(postulacion.getEstado())
                && !"ACEPTADA".equalsIgnoreCase(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_NO_RETIRABLE",
                    "Solo se pueden retirar postulaciones pendientes o aceptadas sin asignar");
        }
        if ("ACEPTADA".equalsIgnoreCase(postulacion.getEstado())) {
            ofertaRepository.devolverCupo(postulacion.getIdOferta().intValue());
        }
        postulacion.setEstado("RETIRADA");
        postulacionRepository.save(postulacion);
        auditoria.registrar(idUsuario, "RETIRAR_POSTULACION_OFERTA", "postulacion_oferta",
                "Postulacion " + idPostulacion + " retirada por el estudiante");
        return mapear(postulacion);
    }

    @Override
    @Transactional
    public PostulacionOfertaResumen decidir(Long idPostulacion, DecisionOfertaRequest request, Integer idUsuario) {
        if (request == null || request.decision() == null || request.decision().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'decision' es obligatorio");
        }
        PostulacionOferta postulacion = buscar(idPostulacion);
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        autorizacion.validarRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad());
        if (!"PENDIENTE".equalsIgnoreCase(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_YA_DECIDIDA",
                    "La postulacion ya tiene una decision registrada");
        }
        String decision = request.decision().trim().toUpperCase(Locale.ROOT);
        if (esAprobar(decision)) {
            int actualizados = ofertaRepository.descontarCupo(oferta.getId());
            if (actualizados == 0) {
                throw new ApiException(HttpStatus.CONFLICT, "SIN_CUPOS",
                        "La oferta ya no tiene cupos disponibles");
            }
            postulacion.setEstado("ACEPTADA");
        } else if (esRechazar(decision)) {
            postulacion.setEstado("RECHAZADA");
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "DECISION_INVALIDA",
                    "La decision debe ser APROBAR o RECHAZAR");
        }
        postulacionRepository.save(postulacion);
        auditoria.registrar(idUsuario, "DECIDIR_POSTULACION_OFERTA", "postulacion_oferta",
                "Postulacion " + idPostulacion + " quedo en estado " + postulacion.getEstado());
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(postulacion.getIdEstudiante()),
                "DECISION_POSTULACION_OFERTA",
                "Tu postulacion a la oferta '" + oferta.getTitulo() + "' quedo en estado "
                        + postulacion.getEstado());
        return mapear(postulacion);
    }

    @Override
    @Transactional
    public PostulacionOfertaResumen asignar(Long idPostulacion, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        PostulacionOferta postulacion = buscar(idPostulacion);
        if (!"ACEPTADA".equalsIgnoreCase(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_NO_ACEPTADA",
                    "Solo se pueden asignar postulaciones aceptadas por la entidad");
        }
        long asignacionesActivas = postulacionRepository.countByIdEstudianteAndEstadoIn(
                postulacion.getIdEstudiante(), List.of("ASIGNADA", "EN_EJECUCION"));
        if (asignacionesActivas > 0) {
            throw new ApiException(HttpStatus.CONFLICT, "ASIGNACION_ACTIVA",
                    "El estudiante ya tiene una asignacion activa");
        }
        postulacion.setEstado("ASIGNADA");
        postulacionRepository.save(postulacion);
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        auditoria.registrar(idUsuario, "ASIGNAR_POSTULACION_OFERTA", "postulacion_oferta",
                "Postulacion " + idPostulacion + " asignada al estudiante " + postulacion.getIdEstudiante());
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(postulacion.getIdEstudiante()),
                "ASIGNACION_CONFIRMADA",
                "Tu asignacion a la oferta '" + oferta.getTitulo() + "' fue confirmada");
        notificacion.notificar(autorizacion.obtenerRepresentanteDeEntidad(oferta.getIdEntidad()).getIdUsuario(),
                "ASIGNACION_CONFIRMADA",
                "Se confirmo la asignacion de un estudiante en la oferta '" + oferta.getTitulo() + "'");
        return mapear(postulacion);
    }

    private PostulacionOferta buscar(Long id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "POSTULACION_INVALIDA",
                    "El id de la postulacion no es valido");
        }
        return postulacionRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "POSTULACION_NO_ENCONTRADA",
                        "La postulacion indicada no existe"));
    }

    private OfertaLaborSocial buscarOferta(Long id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "OFERTA_INVALIDA", "El id de la oferta no es valido");
        }
        return ofertaRepository.findById(id.intValue())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OFERTA_NO_ENCONTRADA",
                        "La oferta indicada no existe"));
    }

    private void validarVigencia(OfertaLaborSocial oferta) {
        LocalDate hoy = LocalDate.now();
        if (oferta.getFechaInicio() != null && hoy.isBefore(oferta.getFechaInicio())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_FUERA_DE_PLAZO",
                    "La oferta aun no ha iniciado");
        }
        if (oferta.getFechaFin() != null && hoy.isAfter(oferta.getFechaFin())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_FUERA_DE_PLAZO",
                    "La oferta ya finalizo");
        }
    }

    private boolean esAprobar(String decision) {
        return decision.equals("APROBAR") || decision.equals("ACEPTAR")
                || decision.equals("APROBADA") || decision.equals("ACEPTADA")
                || decision.equals("APROBADO");
    }

    private boolean esRechazar(String decision) {
        return decision.equals("RECHAZAR") || decision.equals("RECHAZADA") || decision.equals("RECHAZADO");
    }

    private PostulacionOfertaResumen mapear(PostulacionOferta postulacion) {
        return new PostulacionOfertaResumen(postulacion.getId(), postulacion.getIdEstudiante(),
                postulacion.getIdOferta(), postulacion.getFechaPostulacion(), postulacion.getEstado());
    }
}
