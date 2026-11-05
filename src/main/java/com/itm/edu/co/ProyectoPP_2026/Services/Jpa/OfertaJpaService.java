package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.ActualizarOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.CrearOfertaRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.OfertaDtos.OfertaResumen;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.EntidadReceptoraJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.OfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.OfertaJpaServiceInterface;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OfertaJpaService implements OfertaJpaServiceInterface {

    private final OfertaJpaRepository ofertaRepository;
    private final EntidadReceptoraJpaRepository entidadRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;

    public OfertaJpaService(OfertaJpaRepository ofertaRepository,
                            EntidadReceptoraJpaRepository entidadRepository,
                            AutorizacionJpaService autorizacion,
                            AuditoriaJpaService auditoria) {
        this.ofertaRepository = ofertaRepository;
        this.entidadRepository = entidadRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
    }

    @Override
    @Transactional
    public OfertaResumen crear(CrearOfertaRequest request, Integer idUsuario) {
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        if (request.idEntidad() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'idEntidad' es obligatorio");
        }
        autorizacion.validarRepresentanteDeEntidad(idUsuario, request.idEntidad());
        EntidadReceptora entidad = entidadRepository.findById(request.idEntidad())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "ENTIDAD_NO_ENCONTRADA",
                        "La entidad indicada no existe"));
        if (!esEntidadAprobada(entidad.getEstadoValidacion())) {
            throw new ApiException(HttpStatus.CONFLICT, "ENTIDAD_NO_APROBADA",
                    "La entidad debe estar aprobada para publicar ofertas");
        }
        validarDatos(request.titulo(), request.descripcion(), request.cuposDisponibles(),
                request.horasOfrecidas(), request.fechaInicio(), request.fechaFin(),
                Boolean.TRUE.equals(request.publicar()));
        OfertaLaborSocial oferta = new OfertaLaborSocial();
        oferta.setIdEntidad(request.idEntidad());
        oferta.setTitulo(request.titulo().trim());
        oferta.setDescripcion(request.descripcion().trim());
        oferta.setCuposDisponibles(request.cuposDisponibles());
        oferta.setHorasOfrecidas(request.horasOfrecidas());
        oferta.setHorario(limpiar(request.horario()));
        oferta.setUbicacion(limpiar(request.ubicacion()));
        oferta.setFechaInicio(request.fechaInicio());
        oferta.setFechaFin(request.fechaFin());
        oferta.setEstado(Boolean.TRUE.equals(request.publicar()) ? "ACTIVA" : "BORRADOR");
        ofertaRepository.save(oferta);
        auditoria.registrar(idUsuario, "CREAR_OFERTA", "oferta_labor_social",
                "Oferta " + oferta.getId() + " creada en estado " + oferta.getEstado());
        return mapear(oferta);
    }

    @Override
    @Transactional
    public OfertaResumen actualizar(Integer id, ActualizarOfertaRequest request, Integer idUsuario) {
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        OfertaLaborSocial oferta = buscar(id);
        autorizacion.validarRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad());
        if (!"BORRADOR".equalsIgnoreCase(oferta.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_NO_EDITABLE",
                    "Solo se pueden editar ofertas en estado BORRADOR");
        }
        validarDatos(request.titulo(), request.descripcion(), request.cuposDisponibles(),
                request.horasOfrecidas(), request.fechaInicio(), request.fechaFin(), false);
        oferta.setTitulo(request.titulo().trim());
        oferta.setDescripcion(request.descripcion().trim());
        oferta.setCuposDisponibles(request.cuposDisponibles());
        oferta.setHorasOfrecidas(request.horasOfrecidas());
        oferta.setHorario(limpiar(request.horario()));
        oferta.setUbicacion(limpiar(request.ubicacion()));
        oferta.setFechaInicio(request.fechaInicio());
        oferta.setFechaFin(request.fechaFin());
        ofertaRepository.save(oferta);
        auditoria.registrar(idUsuario, "ACTUALIZAR_OFERTA", "oferta_labor_social",
                "Oferta " + id + " actualizada");
        return mapear(oferta);
    }

    @Override
    @Transactional
    public OfertaResumen publicar(Integer id, Integer idUsuario) {
        OfertaLaborSocial oferta = buscar(id);
        autorizacion.validarRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad());
        if (!"BORRADOR".equalsIgnoreCase(oferta.getEstado())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_NO_PUBLICABLE",
                    "Solo se pueden publicar ofertas en estado BORRADOR");
        }
        validarDatos(oferta.getTitulo(), oferta.getDescripcion(), oferta.getCuposDisponibles(),
                oferta.getHorasOfrecidas(), oferta.getFechaInicio(), oferta.getFechaFin(), true);
        oferta.setEstado("ACTIVA");
        ofertaRepository.save(oferta);
        auditoria.registrar(idUsuario, "PUBLICAR_OFERTA", "oferta_labor_social",
                "Oferta " + id + " publicada");
        return mapear(oferta);
    }

    @Override
    @Transactional
    public OfertaResumen cerrar(Integer id, Integer idUsuario) {
        OfertaLaborSocial oferta = buscar(id);
        autorizacion.validarRepresentanteDeEntidad(idUsuario, oferta.getIdEntidad());
        if (!"CERRADA".equalsIgnoreCase(oferta.getEstado())) {
            oferta.setEstado("CERRADA");
            ofertaRepository.save(oferta);
            auditoria.registrar(idUsuario, "CERRAR_OFERTA", "oferta_labor_social",
                    "Oferta " + id + " cerrada");
        }
        return mapear(oferta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfertaResumen> buscar(Integer idEntidad, Integer idMunicipio, String estado, String texto) {
        String estadoFiltro = estado == null || estado.isBlank() ? "ACTIVA" : estado.trim().toUpperCase(Locale.ROOT);
        Specification<OfertaLaborSocial> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"TODAS".equals(estadoFiltro)) {
                predicates.add(builder.equal(builder.upper(root.get("estado")), estadoFiltro));
            }
            if (idEntidad != null) {
                predicates.add(builder.equal(root.get("idEntidad"), idEntidad));
            }
            if (texto != null && !texto.isBlank()) {
                String patron = "%" + texto.trim().toLowerCase(Locale.ROOT) + "%";
                predicates.add(builder.or(
                        builder.like(builder.lower(root.get("titulo")), patron),
                        builder.like(builder.lower(root.get("descripcion")), patron)));
            }
            if (idMunicipio != null) {
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<EntidadReceptora> entidad = subquery.from(EntidadReceptora.class);
                subquery.select(entidad.get("id"))
                        .where(builder.equal(entidad.get("idMunicipio"), idMunicipio));
                predicates.add(root.get("idEntidad").in(subquery));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return ofertaRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "fechaInicio")).stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OfertaResumen obtener(Integer id) {
        return mapear(buscar(id));
    }

    private OfertaLaborSocial buscar(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "OFERTA_INVALIDA", "El id de la oferta no es valido");
        }
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OFERTA_NO_ENCONTRADA",
                        "La oferta indicada no existe"));
    }

    private void validarDatos(String titulo, String descripcion, Integer cupos, Integer horas,
                              LocalDate fechaInicio, LocalDate fechaFin, boolean exigirVigencia) {
        if (titulo == null || titulo.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'titulo' es obligatorio");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo 'descripcion' es obligatorio");
        }
        if (cupos == null || cupos < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CUPOS_INVALIDOS", "Los cupos deben ser mayores a cero");
        }
        if (horas == null || horas < 1) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "HORAS_INVALIDAS",
                    "Las horas ofrecidas deben ser mayores a cero");
        }
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "FECHAS_INVALIDAS",
                    "La fecha de inicio no puede ser posterior a la fecha fin");
        }
        if (exigirVigencia && fechaFin != null && fechaFin.isBefore(LocalDate.now())) {
            throw new ApiException(HttpStatus.CONFLICT, "OFERTA_VENCIDA",
                    "La oferta ya se encuentra vencida");
        }
    }

    private boolean esEntidadAprobada(String estado) {
        return estado != null
                && (estado.equalsIgnoreCase("APROBADO") || estado.equalsIgnoreCase("APROBADA"));
    }

    private String limpiar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private OfertaResumen mapear(OfertaLaborSocial oferta) {
        return new OfertaResumen(oferta.getId(), oferta.getIdEntidad(), oferta.getTitulo(),
                oferta.getDescripcion(), oferta.getCuposDisponibles(), oferta.getHorasOfrecidas(),
                oferta.getHorario(), oferta.getUbicacion(), oferta.getFechaInicio(), oferta.getFechaFin(),
                oferta.getEstado());
    }
}
