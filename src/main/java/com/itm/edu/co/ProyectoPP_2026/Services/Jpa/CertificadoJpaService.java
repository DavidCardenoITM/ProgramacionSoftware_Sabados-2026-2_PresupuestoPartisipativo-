package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.CertificadoResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.GenerarCertificadoRequest;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.CertificadoJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.OfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionOfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.RegistroHorasJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.CertificadoJpaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class CertificadoJpaService implements CertificadoJpaServiceInterface {

    private final CertificadoJpaRepository certificadoRepository;
    private final PostulacionOfertaJpaRepository postulacionRepository;
    private final RegistroHorasJpaRepository registroRepository;
    private final OfertaJpaRepository ofertaRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;
    private final NotificacionJpaService notificacion;

    public CertificadoJpaService(CertificadoJpaRepository certificadoRepository,
                                 PostulacionOfertaJpaRepository postulacionRepository,
                                 RegistroHorasJpaRepository registroRepository,
                                 OfertaJpaRepository ofertaRepository,
                                 AutorizacionJpaService autorizacion,
                                 AuditoriaJpaService auditoria,
                                 NotificacionJpaService notificacion) {
        this.certificadoRepository = certificadoRepository;
        this.postulacionRepository = postulacionRepository;
        this.registroRepository = registroRepository;
        this.ofertaRepository = ofertaRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
        this.notificacion = notificacion;
    }

    @Override
    @Transactional
    public CertificadoResumen generar(GenerarCertificadoRequest request, Integer idUsuario) {
        if (request == null || request.idEstudiante() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idEstudiante' es obligatorio");
        }
        Long idEstudiante = request.idEstudiante();
        autorizacion.validarEstudiante(idUsuario, idEstudiante);
        autorizacion.obtenerEstudiante(idEstudiante);
        if (certificadoRepository.existsByIdEstudiante(idEstudiante)) {
            throw new ApiException(HttpStatus.CONFLICT, "CERTIFICADO_EXISTENTE",
                    "El estudiante ya tiene un certificado generado");
        }
        List<PostulacionOferta> postulaciones = postulacionRepository
                .findByIdEstudianteOrderByFechaPostulacionDesc(idEstudiante);
        PostulacionOferta asignacion = postulaciones.stream()
                .filter(postulacion -> estadoCertificable(postulacion.getEstado()))
                .findFirst()
                .orElseThrow(() -> new ApiException(HttpStatus.CONFLICT, "SIN_ASIGNACION",
                        "El estudiante no tiene una asignacion activa o finalizada"));
        OfertaLaborSocial oferta = buscarOferta(asignacion.getIdOferta());
        List<Long> idsPostulaciones = postulaciones.stream().map(PostulacionOferta::getId).toList();
        double horasValidadas = nvl(registroRepository.sumHorasByPostulacionesAndEstado(
                idsPostulaciones, List.of("VALIDADO")));
        if (horasValidadas < oferta.getHorasOfrecidas()) {
            throw new ApiException(HttpStatus.CONFLICT, "HORAS_INSUFICIENTES",
                    "El estudiante aun no completa las horas requeridas por la oferta");
        }
        Certificado certificado = new Certificado();
        certificado.setIdEstudiante(idEstudiante);
        certificado.setFechaEmision(LocalDate.now());
        certificado.setHorasTotales((int) Math.round(horasValidadas));
        certificado.setCodigoVerificacion(generarCodigo(idEstudiante));
        certificadoRepository.save(certificado);
        asignacion.setEstado("CERTIFICADA");
        postulacionRepository.save(asignacion);
        auditoria.registrar(idUsuario, "GENERAR_CERTIFICADO", "certificado",
                "Certificado " + certificado.getId() + " generado para el estudiante " + idEstudiante);
        notificacion.notificar(autorizacion.idUsuarioDeEstudiante(idEstudiante), "CERTIFICADO_GENERADO",
                "Tu certificado de labor social fue generado con el codigo "
                        + certificado.getCodigoVerificacion());
        return mapear(certificado);
    }

    @Override
    @Transactional(readOnly = true)
    public CertificadoResumen consultar(String codigoVerificacion) {
        if (codigoVerificacion == null || codigoVerificacion.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CODIGO_REQUERIDO",
                    "El codigo de verificacion es obligatorio");
        }
        return certificadoRepository.findByCodigoVerificacionIgnoreCase(codigoVerificacion.trim())
                .map(this::mapear)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "CERTIFICADO_NO_ENCONTRADO",
                        "No existe un certificado con el codigo indicado"));
    }

    @Override
    @Transactional(readOnly = true)
    public CertificadoResumen obtenerPorEstudiante(Long idEstudiante, Integer idUsuario) {
        autorizacion.validarEstudiante(idUsuario, idEstudiante);
        return certificadoRepository.findByIdEstudiante(idEstudiante)
                .map(this::mapear)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "CERTIFICADO_NO_ENCONTRADO",
                        "El estudiante aun no tiene un certificado generado"));
    }

    private String generarCodigo(Long idEstudiante) {
        String codigo;
        do {
            codigo = "CERT-" + idEstudiante + "-"
                    + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-"
                    + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
        } while (certificadoRepository.findByCodigoVerificacionIgnoreCase(codigo).isPresent());
        return codigo;
    }

    private OfertaLaborSocial buscarOferta(Long id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "OFERTA_INVALIDA", "El id de la oferta no es valido");
        }
        return ofertaRepository.findById(id.intValue())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OFERTA_NO_ENCONTRADA",
                        "La oferta indicada no existe"));
    }

    private boolean estadoCertificable(String estado) {
        return "ASIGNADA".equalsIgnoreCase(estado)
                || "EN_EJECUCION".equalsIgnoreCase(estado)
                || "FINALIZADA".equalsIgnoreCase(estado);
    }

    private double nvl(Double valor) {
        return valor == null ? 0 : valor;
    }

    private CertificadoResumen mapear(Certificado certificado) {
        return new CertificadoResumen(certificado.getId(), certificado.getIdEstudiante(),
                certificado.getFechaEmision(), certificado.getHorasTotales(),
                certificado.getCodigoVerificacion());
    }
}
