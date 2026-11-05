package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HistorialDtos.HistorialEstudianteResumen;
import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.EstudianteJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.OfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.PostulacionOfertaJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.RegistroHorasJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.UsuarioJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.HistorialEntidadJpaServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistorialEntidadJpaService implements HistorialEntidadJpaServiceInterface {

    private final OfertaJpaRepository ofertaRepository;
    private final PostulacionOfertaJpaRepository postulacionRepository;
    private final EstudianteJpaRepository estudianteRepository;
    private final UsuarioJpaRepository usuarioRepository;
    private final RegistroHorasJpaRepository registroRepository;
    private final AutorizacionJpaService autorizacion;

    public HistorialEntidadJpaService(OfertaJpaRepository ofertaRepository,
                                      PostulacionOfertaJpaRepository postulacionRepository,
                                      EstudianteJpaRepository estudianteRepository,
                                      UsuarioJpaRepository usuarioRepository,
                                      RegistroHorasJpaRepository registroRepository,
                                      AutorizacionJpaService autorizacion) {
        this.ofertaRepository = ofertaRepository;
        this.postulacionRepository = postulacionRepository;
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.registroRepository = registroRepository;
        this.autorizacion = autorizacion;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialEstudianteResumen> listar(Integer idEntidad, Integer idUsuario) {
        autorizacion.validarRepresentanteDeEntidad(idUsuario, idEntidad);
        List<OfertaLaborSocial> ofertas = ofertaRepository.findByIdEntidadOrderByFechaInicioDesc(idEntidad);
        if (ofertas.isEmpty()) {
            return List.of();
        }
        Map<Integer, String> titulos = ofertas.stream()
                .collect(Collectors.toMap(OfertaLaborSocial::getId, OfertaLaborSocial::getTitulo));
        List<Long> idsOfertas = ofertas.stream().map(oferta -> oferta.getId().longValue()).toList();
        List<PostulacionOferta> postulaciones = postulacionRepository
                .findByIdOfertaInAndEstadoInOrderByFechaPostulacionAsc(idsOfertas,
                        List.of("ASIGNADA", "EN_EJECUCION", "FINALIZADA", "CERTIFICADA"));
        return postulaciones.stream()
                .map(postulacion -> mapear(postulacion, titulos))
                .toList();
    }

    private HistorialEstudianteResumen mapear(PostulacionOferta postulacion, Map<Integer, String> titulos) {
        Estudiante estudiante = estudianteRepository.findById(postulacion.getIdEstudiante().intValue()).orElse(null);
        Usuarios usuario = estudiante == null ? null
                : usuarioRepository.findById(estudiante.getIdUsuario()).orElse(null);
        String nombre = usuario == null ? null : usuario.getNombres() + " " + usuario.getApellidos();
        Double horasValidadas = registroRepository.sumHorasByEstado(postulacion.getId(), List.of("VALIDADO"));
        double horas = horasValidadas == null ? 0 : horasValidadas;
        return new HistorialEstudianteResumen(postulacion.getId(), postulacion.getIdEstudiante(),
                nombre, estudiante == null ? null : estudiante.getDocumentoIdentidad(),
                titulos.get(postulacion.getIdOferta().intValue()), postulacion.getEstado(),
                postulacion.getFechaPostulacion(), horas);
    }
}
