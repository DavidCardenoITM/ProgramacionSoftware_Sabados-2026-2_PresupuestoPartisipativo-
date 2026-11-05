package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistrarHorasRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistroHorasResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ResumenHoras;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ValidarHorasRequest;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.OfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionOfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.RegistroHorasJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.ValidacionFirmaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.RegistroHorasJpaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

@Service
public class RegistroHorasJpaService implements RegistroHorasJpaServiceInterface {

    private static final double MAX_HORAS_DIA = 8.0;
    private static final DateTimeFormatter HORA_COMPLETA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter HORA_CORTA = DateTimeFormatter.ofPattern("H:mm");

    private final RegistroHorasJpaRepository registroRepository;
    private final PostulacionOfertaJpaRepository postulacionRepository;
    private final OfertaJpaRepository ofertaRepository;
    private final ValidacionFirmaJpaRepository validacionRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;
    private final NotificacionJpaService notificacion;

    public RegistroHorasJpaService(RegistroHorasJpaRepository registroRepository,
                                   PostulacionOfertaJpaRepository postulacionRepository,
                                   OfertaJpaRepository ofertaRepository,
                                   ValidacionFirmaJpaRepository validacionRepository,
                                   AutorizacionJpaService autorizacion,
                                   AuditoriaJpaService auditoria,
                                   NotificacionJpaService notificacion) {
        this.registroRepository = registroRepository;
        this.postulacionRepository = postulacionRepository;
        this.ofertaRepository = ofertaRepository;
        this.validacionRepository = validacionRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
        this.notificacion = notificacion;
    }

    @Override
    @Transactional
    public RegistroHorasResumen registrar(RegistrarHorasRequest request, Integer idUsuario) {
        DatosJornada datos = construirDatos(request, null);
        PostulacionOferta postulacion = buscarPostulacion(datos.idPostulacionOferta());
        autorizacion.validarEstudiante(idUsuario, postulacion.getIdEstudiante());
        if (!permiteRegistrarHoras(postulacion.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "ASIGNACION_NO_ACTIVA",
                    "Solo se pueden registrar horas con una asignacion activa");
        }
        validarJornada(datos, postulacion, null);
        RegistroHoras registro = new RegistroHoras();
        aplicarDatos(registro, datos);
        registro.setEstadoValidacion("PENDIENTE");
        registroRepository.save(registro);
        auditoria.registrar(idUsuario, "REGISTRAR_HORAS", "registro_horas",
                "Registro " + registro.getId() + " de " + datos.horasTrabajadas() + " horas el " + datos.fecha());
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        notificacion.notificar(autorizacion.obtenerRepresentanteDeEntidad(oferta.getIdEntidad()).getIdUsuario(),
                "HORAS_REGISTRADAS",
                "El estudiante registro " + datos.horasTrabajadas() + " horas en la oferta '"
                        + oferta.getTitulo() + "'");
        return mapear(registro);
    }

    @Override
    @Transactional
    public RegistroHorasResumen actualizar(Long id, RegistrarHorasRequest request, Integer idUsuario) {
        RegistroHoras registro = buscar(id);
        PostulacionOferta postulacion = buscarPostulacion(registro.getIdPostulacionOferta());
        autorizacion.validarEstudiante(idUsuario, postulacion.getIdEstudiante());
        if (!"PENDIENTE".equalsIgnoreCase(registro.getEstadoValidacion())) {
            throw new ApiException(HttpStatus.CONFLICT, "REGISTRO_NO_EDITABLE",
                    "Solo se pueden editar registros de horas pendientes de validacion");
        }
        DatosJornada datos = construirDatos(request, registro.getIdPostulacionOferta());
        if (!datos.idPostulacionOferta().equals(registro.getIdPostulacionOferta())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "POSTULACION_INVALIDA",
                    "No se puede cambiar la postulacion del registro de horas");
        }
        validarJornada(datos, postulacion, registro);
        aplicarDatos(registro, datos);
        registroRepository.save(registro);
        auditoria.registrar(idUsuario, "ACTUALIZAR_HORAS", "registro_horas",
                "Registro " + id + " actualizado a " + datos.horasTrabajadas() + " horas el " + datos.fecha());
        return mapear(registro);
    }

    @Override
    @Transactional
    public void eliminar(Long id, Integer idUsuario) {
        RegistroHoras registro = buscar(id);
        PostulacionOferta postulacion = buscarPostulacion(registro.getIdPostulacionOferta());
        autorizacion.validarEstudiante(idUsuario, postulacion.getIdEstudiante());
        if (!"PENDIENTE".equalsIgnoreCase(registro.getEstadoValidacion())) {
            throw new ApiException(HttpStatus.CONFLICT, "REGISTRO_NO_ELIMINABLE",
                    "Solo se pueden eliminar registros de horas pendientes de validacion");
        }
        registroRepository.delete(registro);
        auditoria.registrar(idUsuario, "ELIMINAR_HORAS", "registro_horas", "Registro " + id + " eliminado");
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroHorasResumen> listar(Long idPostulacionOferta, Integer idUsuario) {
        PostulacionOferta postulacion = buscarPostulacion(idPostulacionOferta);
        validarAcceso(idUsuario, postulacion);
        return registroRepository.findByIdPostulacionOfertaOrderByFechaDesc(idPostulacionOferta).stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional
    public RegistroHorasResumen validar(Long id, ValidarHorasRequest request, Integer idUsuario) {
        if (request == null || request.decision() == null || request.decision().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'decision' es obligatorio");
        }
        RegistroHoras registro = buscar(id);
        PostulacionOferta postulacion = buscarPostulacion(registro.getIdPostulacionOferta());
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        var representante = autorizacion.obtenerRepresentanteQueValida(idUsuario, oferta.getIdEntidad());
        if (!"PENDIENTE".equalsIgnoreCase(registro.getEstadoValidacion())) {
            throw new ApiException(HttpStatus.CONFLICT, "REGISTRO_YA_VALIDADO",
                    "El registro de horas ya tiene una validacion registrada");
        }
        String decision = request.decision().trim().toUpperCase(Locale.ROOT);
        boolean aprobado;
        if (esAprobar(decision)) {
            aprobado = true;
            registro.setEstadoValidacion("VALIDADO");
        } else if (esRechazar(decision)) {
            aprobado = false;
            if (request.observaciones() == null || request.observaciones().isBlank()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "OBSERVACION_REQUERIDA",
                        "Debe indicar la observacion del rechazo");
            }
            registro.setEstadoValidacion("RECHAZADO");
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "DECISION_INVALIDA",
                    "La decision debe ser APROBAR o RECHAZAR");
        }
        registroRepository.save(registro);
        ValidacionFirma validacion = new ValidacionFirma();
        validacion.setIdRegistroHoras(registro.getId().intValue());
        validacion.setIdRepresentante(representante.getId());
        validacion.setFechaValidacion(LocalDateTime.now());
        validacion.setResultado(aprobado ? "APROBADO" : "RECHAZADO");
        validacion.setObservaciones(limpiar(request.observaciones()));
        validacionRepository.save(validacion);
        auditoria.registrar(idUsuario, "VALIDAR_HORAS", "registro_horas",
                "Registro " + id + " quedo en estado " + registro.getEstadoValidacion());
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(postulacion.getIdEstudiante()),
                "DECISION_HORAS",
                "Tu registro de horas del " + registro.getFecha() + " quedo en estado "
                        + registro.getEstadoValidacion());
        return mapear(registro);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumenHoras resumen(Long idPostulacionOferta, Integer idUsuario) {
        PostulacionOferta postulacion = buscarPostulacion(idPostulacionOferta);
        validarAcceso(idUsuario, postulacion);
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        double validadas = nvl(registroRepository.sumHorasByEstado(idPostulacionOferta, List.of("VALIDADO")));
        double pendientes = nvl(registroRepository.sumHorasByEstado(idPostulacionOferta, List.of("PENDIENTE")));
        double rechazadas = nvl(registroRepository.sumHorasByEstado(idPostulacionOferta, List.of("RECHAZADO")));
        int requeridas = oferta.getHorasOfrecidas();
        double restantes = Math.max(0, requeridas - validadas);
        return new ResumenHoras(idPostulacionOferta, requeridas, redondear(validadas), redondear(pendientes),
                redondear(rechazadas), redondear(restantes), validadas >= requeridas);
    }

    private DatosJornada construirDatos(RegistrarHorasRequest request, Long idPostulacionPorDefecto) {
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        Long idPostulacion = request.idPostulacionOferta() != null
                ? request.idPostulacionOferta()
                : idPostulacionPorDefecto;
        if (idPostulacion == null || idPostulacion <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idPostulacionOferta' es obligatorio");
        }
        if (request.fecha() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'fecha' es obligatorio");
        }
        if (request.fecha().isAfter(LocalDate.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "FECHA_FUTURA",
                    "No se pueden registrar horas de una fecha futura");
        }
        if (request.descripcionActividad() == null || request.descripcionActividad().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'descripcionActividad' es obligatorio");
        }
        LocalTime inicio = parseHora(request.horaInicio(), "horaInicio");
        LocalTime fin = parseHora(request.horaFin(), "horaFin");
        if (!fin.isAfter(inicio)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "HORAS_INVALIDAS",
                    "La hora de fin debe ser posterior a la hora de inicio");
        }
        long minutos = Duration.between(inicio, fin).toMinutes();
        double horas = redondear(minutos / 60.0);
        if (horas <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "HORAS_INVALIDAS",
                    "La jornada debe tener una duracion mayor a cero");
        }
        if (horas > MAX_HORAS_DIA) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JORNADA_EXCEDIDA",
                    "Una jornada no puede superar las 8 horas");
        }
        if (request.horasTrabajadas() != null && Math.abs(request.horasTrabajadas() - horas) > 0.05) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "HORAS_INCONSISTENTES",
                    "Las horas trabajadas no coinciden con el rango de horas indicado");
        }
        return new DatosJornada(idPostulacion, request.fecha(), inicio.format(HORA_COMPLETA),
                fin.format(HORA_COMPLETA), horas, request.descripcionActividad().trim());
    }

    private void validarJornada(DatosJornada datos, PostulacionOferta postulacion, RegistroHoras actual) {
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        List<RegistroHoras> registrosDelDia = registroRepository
                .findByIdPostulacionOfertaAndFechaAndEstadoValidacionNot(
                        postulacion.getId(), datos.fecha(), "RECHAZADO");
        LocalTime inicioNuevo = LocalTime.parse(datos.horaInicio(), HORA_COMPLETA);
        LocalTime finNuevo = LocalTime.parse(datos.horaFin(), HORA_COMPLETA);
        double horasDelDia = 0;
        boolean cruce = false;
        for (RegistroHoras registro : registrosDelDia) {
            if (actual != null && registro.getId().equals(actual.getId())) {
                continue;
            }
            horasDelDia += nvl(registro.getHorasTrabajadas());
            LocalTime inicio = parseHora(registro.getHoraInicio(), "horaInicio");
            LocalTime fin = parseHora(registro.getHoraFin(), "horaFin");
            if (inicioNuevo.isBefore(fin) && finNuevo.isAfter(inicio)) {
                cruce = true;
            }
        }
        if (cruce) {
            throw new ApiException(HttpStatus.CONFLICT, "CRUCE_HORARIO",
                    "El rango de horas se cruza con otro registro del mismo dia");
        }
        if (horasDelDia + datos.horasTrabajadas() > MAX_HORAS_DIA) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "LIMITE_DIARIO",
                    "La suma de horas del dia no puede superar las 8 horas");
        }
        double totalActual = nvl(registroRepository.sumHorasByEstado(
                postulacion.getId(), List.of("PENDIENTE", "VALIDADO")));
        if (actual != null) {
            totalActual -= nvl(actual.getHorasTrabajadas());
        }
        if (totalActual + datos.horasTrabajadas() > oferta.getHorasOfrecidas()) {
            throw new ApiException(HttpStatus.CONFLICT, "HORAS_EXCEDIDAS",
                    "La suma de horas supera las horas ofrecidas por la oferta");
        }
    }

    private void validarAcceso(Integer idUsuario, PostulacionOferta postulacion) {
        Usuarios usuario = autorizacion.obtenerUsuario(idUsuario);
        if (autorizacion.esAdministrador(usuario)) {
            return;
        }
        Estudiante estudiante = autorizacion.obtenerEstudiante(postulacion.getIdEstudiante());
        if (estudiante.getIdUsuario().equals(idUsuario)) {
            return;
        }
        OfertaLaborSocial oferta = buscarOferta(postulacion.getIdOferta());
        if (autorizacion.esRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad())) {
            return;
        }
        throw new ApiException(HttpStatus.FORBIDDEN, "ACCESO_DENEGADO",
                "El usuario no puede consultar esta postulacion");
    }

    private RegistroHoras buscar(Long id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "REGISTRO_INVALIDO",
                    "El id del registro de horas no es valido");
        }
        return registroRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "REGISTRO_NO_ENCONTRADO",
                        "El registro de horas indicado no existe"));
    }

    private PostulacionOferta buscarPostulacion(Long id) {
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

    private void aplicarDatos(RegistroHoras registro, DatosJornada datos) {
        registro.setIdPostulacionOferta(datos.idPostulacionOferta());
        registro.setFecha(datos.fecha());
        registro.setHoraInicio(datos.horaInicio());
        registro.setHoraFin(datos.horaFin());
        registro.setHorasTrabajadas(datos.horasTrabajadas());
        registro.setDescripcionActividad(datos.descripcionActividad());
    }

    private LocalTime parseHora(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo '" + campo + "' es obligatorio");
        }
        String texto = valor.trim();
        try {
            return LocalTime.parse(texto, HORA_COMPLETA);
        } catch (DateTimeParseException primero) {
            try {
                return LocalTime.parse(texto, HORA_CORTA);
            } catch (DateTimeParseException segundo) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "HORA_INVALIDA",
                        "El campo '" + campo + "' debe tener formato HH:mm o HH:mm:ss");
            }
        }
    }

    private boolean permiteRegistrarHoras(String estado) {
        return "ASIGNADA".equalsIgnoreCase(estado) || "EN_EJECUCION".equalsIgnoreCase(estado);
    }

    private boolean esAprobar(String decision) {
        return decision.equals("APROBAR") || decision.equals("APROBADO")
                || decision.equals("VALIDAR") || decision.equals("VALIDADO")
                || decision.equals("VALIDADA");
    }

    private boolean esRechazar(String decision) {
        return decision.equals("RECHAZAR") || decision.equals("RECHAZADO") || decision.equals("RECHAZADA");
    }

    private double nvl(Double valor) {
        return valor == null ? 0 : valor;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    private String limpiar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private RegistroHorasResumen mapear(RegistroHoras registro) {
        return new RegistroHorasResumen(registro.getId(), registro.getIdPostulacionOferta(),
                registro.getFecha(), registro.getHoraInicio(), registro.getHoraFin(),
                registro.getHorasTrabajadas(), registro.getDescripcionActividad(),
                registro.getEstadoValidacion());
    }

    private record DatosJornada(
            Long idPostulacionOferta,
            LocalDate fecha,
            String horaInicio,
            String horaFin,
            double horasTrabajadas,
            String descripcionActividad) {
    }
}
