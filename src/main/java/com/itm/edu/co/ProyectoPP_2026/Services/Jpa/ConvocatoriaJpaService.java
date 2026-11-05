package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.ConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.CrearConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.DecisionConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostulacionConvocatoriaResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.ConvocatoriaDtos.PostularConvocatoriaRequest;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.ConvocatoriaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.MunicipioJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionConvocatoriaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.ConvocatoriaJpaServiceInterface;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class ConvocatoriaJpaService implements ConvocatoriaJpaServiceInterface {

    private final ConvocatoriaJpaRepository convocatoriaRepository;
    private final PostulacionConvocatoriaJpaRepository postulacionRepository;
    private final MunicipioJpaRepository municipioRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;
    private final NotificacionJpaService notificacion;

    public ConvocatoriaJpaService(ConvocatoriaJpaRepository convocatoriaRepository,
                                  PostulacionConvocatoriaJpaRepository postulacionRepository,
                                  MunicipioJpaRepository municipioRepository,
                                  AutorizacionJpaService autorizacion,
                                  AuditoriaJpaService auditoria,
                                  NotificacionJpaService notificacion) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.postulacionRepository = postulacionRepository;
        this.municipioRepository = municipioRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
        this.notificacion = notificacion;
    }

    @Override
    @Transactional
    public ConvocatoriaResumen crear(CrearConvocatoriaRequest request, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        if (request.idMunicipio() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idMunicipio' es obligatorio");
        }
        if (request.fechaApertura() == null || request.fechaCierre() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "Las fechas de apertura y cierre son obligatorias");
        }
        if (request.cupos() == null || request.cupos() < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CUPOS_INVALIDOS", "Los cupos deben ser mayores a cero");
        }
        String nombre = validarTexto(request.nombre(), "nombre");
        municipioRepository.findById(request.idMunicipio())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "MUNICIPIO_NO_ENCONTRADO",
                        "El municipio indicado no existe"));
        if (request.fechaApertura().isAfter(request.fechaCierre())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "FECHAS_INVALIDAS",
                    "La fecha de apertura no puede ser posterior a la fecha de cierre");
        }
        if (convocatoriaRepository.existsByIdMunicipioAndNombreIgnoreCase(request.idMunicipio(), nombre)) {
            throw new ApiException(HttpStatus.CONFLICT, "CONVOCATORIA_DUPLICADA",
                    "Ya existe una convocatoria con ese nombre en el municipio");
        }
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(request.fechaCierre())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CONVOCATORIA_VENCIDA",
                    "La fecha de cierre de la convocatoria ya paso");
        }
        String estado = hoy.isBefore(request.fechaApertura()) ? "PROGRAMADA" : "ABIERTA";
        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setIdMunicipio(request.idMunicipio());
        convocatoria.setNombre(nombre);
        convocatoria.setFechaApertura(request.fechaApertura());
        convocatoria.setFechaCierre(request.fechaCierre());
        convocatoria.setCupos(request.cupos());
        convocatoria.setEstado(estado);
        convocatoriaRepository.save(convocatoria);
        auditoria.registrar(idUsuario, "CREAR_CONVOCATORIA", "convocatoria",
                "Convocatoria " + convocatoria.getId() + " creada en estado " + estado);
        return mapear(convocatoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConvocatoriaResumen> listar(Integer idMunicipio, String estado, boolean soloVigentes) {
        LocalDate hoy = LocalDate.now();
        List<Convocatoria> convocatorias;
        if (soloVigentes && idMunicipio != null) {
            convocatorias = convocatoriaRepository
                    .findByIdMunicipioAndEstadoAndFechaAperturaLessThanEqualAndFechaCierreGreaterThanEqual(
                            idMunicipio, "ABIERTA", hoy, hoy);
        } else if (soloVigentes) {
            convocatorias = convocatoriaRepository
                    .findByEstadoAndFechaAperturaLessThanEqualAndFechaCierreGreaterThanEqual(
                            "ABIERTA", hoy, hoy);
        } else if (idMunicipio != null && estado != null && !estado.isBlank()) {
            convocatorias = convocatoriaRepository.findByIdMunicipioAndEstadoOrderByFechaCierreDesc(
                    idMunicipio, estado.trim().toUpperCase(Locale.ROOT));
        } else if (idMunicipio != null) {
            convocatorias = convocatoriaRepository.findByIdMunicipioOrderByFechaCierreDesc(idMunicipio);
        } else if (estado != null && !estado.isBlank()) {
            convocatorias = convocatoriaRepository.findByEstadoOrderByFechaCierreDesc(
                    estado.trim().toUpperCase(Locale.ROOT));
        } else {
            convocatorias = convocatoriaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCierre"));
        }
        return convocatorias.stream().map(this::mapear).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConvocatoriaResumen obtener(Integer id) {
        return mapear(buscar(id));
    }

    @Override
    @Transactional
    public ConvocatoriaResumen cerrar(Integer id, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        Convocatoria convocatoria = buscar(id);
        if (!"CERRADA".equalsIgnoreCase(convocatoria.getEstado())) {
            convocatoria.setEstado("CERRADA");
            convocatoriaRepository.save(convocatoria);
            auditoria.registrar(idUsuario, "CERRAR_CONVOCATORIA", "convocatoria",
                    "Convocatoria " + id + " cerrada manualmente");
        }
        return mapear(convocatoria);
    }

    @Override
    @Transactional
    public PostulacionConvocatoriaResumen postular(Integer idConvocatoria, PostularConvocatoriaRequest request,
                                                   Integer idUsuario) {
        if (request == null || request.idEstudiante() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idEstudiante' es obligatorio");
        }
        Long idEstudiante = request.idEstudiante().longValue();
        autorizacion.validarEstudiante(idUsuario, idEstudiante);
        Convocatoria convocatoria = buscar(idConvocatoria);
        if (!"ABIERTA".equalsIgnoreCase(convocatoria.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "CONVOCATORIA_NO_ABIERTA",
                    "La convocatoria no esta abierta para recibir postulaciones");
        }
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(convocatoria.getFechaApertura()) || hoy.isAfter(convocatoria.getFechaCierre())) {
            throw new ApiException(HttpStatus.CONFLICT, "CONVOCATORIA_FUERA_DE_PLAZO",
                    "La convocatoria esta fuera del plazo de postulacion");
        }
        if (postulacionRepository.existsByIdEstudianteAndIdConvocatoria(idEstudiante.intValue(), idConvocatoria)) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_DUPLICADA",
                    "El estudiante ya tiene una postulacion en esta convocatoria");
        }
        PostulacionConvocatoria postulacion = new PostulacionConvocatoria();
        postulacion.setIdEstudiante(idEstudiante.intValue());
        postulacion.setIdConvocatoria(idConvocatoria);
        postulacion.setFechaPostulacion(hoy);
        postulacion.setEstado("PENDIENTE");
        postulacionRepository.save(postulacion);
        auditoria.registrar(idUsuario, "POSTULAR_CONVOCATORIA", "postulacion_convocatoria",
                "Estudiante " + idEstudiante + " postulado a convocatoria " + idConvocatoria);
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(idEstudiante), "POSTULACION_CONVOCATORIA",
                "Tu postulacion a la convocatoria '" + convocatoria.getNombre() + "' fue registrada");
        return mapear(postulacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionConvocatoriaResumen> listarPostulaciones(Integer idConvocatoria, String estado,
                                                                    Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        buscar(idConvocatoria);
        List<PostulacionConvocatoria> postulaciones;
        if (estado != null && !estado.isBlank()) {
            postulaciones = postulacionRepository.findByIdConvocatoriaAndEstadoOrderByFechaPostulacionAsc(
                    idConvocatoria, estado.trim().toUpperCase(Locale.ROOT));
        } else {
            postulaciones = postulacionRepository.findByIdConvocatoriaOrderByFechaPostulacionAsc(idConvocatoria);
        }
        return postulaciones.stream().map(this::mapear).toList();
    }

    @Override
    @Transactional
    public PostulacionConvocatoriaResumen decidir(Integer idPostulacion, DecisionConvocatoriaRequest request,
                                                  Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        if (request == null || request.decision() == null || request.decision().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'decision' es obligatorio");
        }
        PostulacionConvocatoria postulacion = buscarPostulacion(idPostulacion);
        if (!"PENDIENTE".equalsIgnoreCase(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "POSTULACION_YA_DECIDIDA",
                    "La postulacion ya tiene una decision registrada");
        }
        String decision = request.decision().trim().toUpperCase(Locale.ROOT);
        if (esAprobar(decision)) {
            Convocatoria convocatoria = buscar(postulacion.getIdConvocatoria());
            long aceptadas = contarAceptadas(convocatoria.getId());
            if (aceptadas >= convocatoria.getCupos()) {
                throw new ApiException(HttpStatus.CONFLICT, "CUPOS_AGOTADOS",
                        "La convocatoria ya no tiene cupos disponibles");
            }
            postulacion.setEstado("ACEPTADA");
            postulacion.setMotivoRechazo(null);
        } else if (esRechazar(decision)) {
            if (request.motivo() == null || request.motivo().isBlank()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "MOTIVO_REQUERIDO",
                        "Debe indicar el motivo del rechazo");
            }
            postulacion.setEstado("RECHAZADA");
            postulacion.setMotivoRechazo(request.motivo().trim());
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "DECISION_INVALIDA",
                    "La decision debe ser APROBAR o RECHAZAR");
        }
        postulacionRepository.save(postulacion);
        auditoria.registrar(idUsuario, "DECIDIR_POSTULACION_CONVOCATORIA", "postulacion_convocatoria",
                "Postulacion " + idPostulacion + " quedo en estado " + postulacion.getEstado());
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(postulacion.getIdEstudiante().longValue()),
                "DECISION_POSTULACION_CONVOCATORIA",
                "Tu postulacion a la convocatoria quedo en estado " + postulacion.getEstado());
        return mapear(postulacion);
    }

    private Convocatoria buscar(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CONVOCATORIA_INVALIDA",
                    "El id de la convocatoria no es valido");
        }
        return convocatoriaRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "CONVOCATORIA_NO_ENCONTRADA",
                        "La convocatoria indicada no existe"));
    }

    private PostulacionConvocatoria buscarPostulacion(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "POSTULACION_INVALIDA",
                    "El id de la postulacion no es valido");
        }
        return postulacionRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "POSTULACION_NO_ENCONTRADA",
                        "La postulacion indicada no existe"));
    }

    private long contarAceptadas(Integer idConvocatoria) {
        return postulacionRepository.countByIdConvocatoriaAndEstado(idConvocatoria, "ACEPTADA")
                + postulacionRepository.countByIdConvocatoriaAndEstado(idConvocatoria, "APROBADA");
    }

    private boolean esAprobar(String decision) {
        return decision.equals("APROBAR") || decision.equals("ACEPTAR")
                || decision.equals("APROBADA") || decision.equals("ACEPTADA")
                || decision.equals("APROBADO");
    }

    private boolean esRechazar(String decision) {
        return decision.equals("RECHAZAR") || decision.equals("RECHAZADA") || decision.equals("RECHAZADO");
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo '" + campo + "' es obligatorio");
        }
        return valor.trim();
    }

    private ConvocatoriaResumen mapear(Convocatoria convocatoria) {
        return new ConvocatoriaResumen(convocatoria.getId(), convocatoria.getIdMunicipio(),
                convocatoria.getNombre(), convocatoria.getFechaApertura(), convocatoria.getFechaCierre(),
                convocatoria.getCupos(), convocatoria.getEstado(), contarAceptadas(convocatoria.getId()));
    }

    private PostulacionConvocatoriaResumen mapear(PostulacionConvocatoria postulacion) {
        return new PostulacionConvocatoriaResumen(postulacion.getId(), postulacion.getIdEstudiante(),
                postulacion.getIdConvocatoria(), postulacion.getFechaPostulacion(), postulacion.getEstado(),
                postulacion.getMotivoRechazo());
    }
}
