package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.OfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.PostulacionOfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.PostulacionOfertaServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostulacionOfertaService implements PostulacionOfertaServiceInterface {

    private final PostulacionOfertaRepositoryInterface repository;
    private final OfertaRepositoryInterface ofertaRepository;

    public PostulacionOfertaService(PostulacionOfertaRepositoryInterface repository,
                                    OfertaRepositoryInterface ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    public List<PostulacionOferta> listarPostulaciones() {
        return repository.listarPostulaciones();
    }

    @Override
    public PostulacionOferta buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la postulacion es invalido");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public PostulacionOferta insertarPostulacion(PostulacionOferta postulacion) {
        validarPostulacion(postulacion);
        if (postulacion.getEstado() == null || postulacion.getEstado().isBlank()) {
            postulacion.setEstado("Pendiente");
        }
        if (postulacion.getFechaPostulacion() == null) {
            postulacion.setFechaPostulacion(LocalDate.now());
        }
        validarOfertaAbierta(postulacion.getIdOferta());
        return repository.insertarPostulacion(postulacion);
    }

    @Override
    public PostulacionOferta actualizarPostulacion(PostulacionOferta postulacion) {
        if (postulacion == null || postulacion.getId() == null) {
            throw new IllegalArgumentException("La postulacion y su id son obligatorios para actualizar");
        }
        if (postulacion.getEstado() != null && !esEstadoValido(postulacion.getEstado())) {
            throw new IllegalArgumentException("Estado invalido. Use: Pendiente, Aprobado, Rechazado");
        }
        if (postulacion.getIdOferta() != null) {
            validarOfertaAbierta(postulacion.getIdOferta());
        }
        return repository.actualizarPostulacion(postulacion);
    }

    @Override
    public Boolean eliminarPostulacion(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la postulacion es invalido");
        }
        PostulacionOferta actual = repository.buscarPorId(id);
        if (actual == null || actual.getId() == null) {
            throw new IllegalArgumentException("La postulacion con id " + id + " no existe");
        }
        return repository.eliminarPostulacion(id);
    }

    private void validarPostulacion(PostulacionOferta postulacion) {
        if (postulacion == null) {
            throw new IllegalArgumentException("La postulacion es obligatoria");
        }
        if (postulacion.getIdEstudiante() == null) {
            throw new IllegalArgumentException("El idEstudiante es obligatorio");
        }
        if (postulacion.getIdOferta() == null) {
            throw new IllegalArgumentException("El idOferta es obligatorio");
        }
        if (postulacion.getEstado() != null && !esEstadoValido(postulacion.getEstado())) {
            throw new IllegalArgumentException("Estado invalido. Use: Pendiente, Aprobado, Rechazado");
        }
    }

    private void validarOfertaAbierta(Long idOferta) {
        OfertaLaborSocial oferta = ofertaRepository.buscarPorId(idOferta.intValue());
        if (oferta == null || oferta.getId() == null) {
            throw new IllegalArgumentException("La oferta con id " + idOferta + " no existe");
        }
        if (oferta.getEstado() != null && oferta.getEstado().equalsIgnoreCase("Cerrada")) {
            throw new IllegalArgumentException("No se puede postular a una oferta cerrada");
        }
    }

    private boolean esEstadoValido(String estado) {
        return estado.equalsIgnoreCase("Pendiente")
                || estado.equalsIgnoreCase("Aprobado")
                || estado.equalsIgnoreCase("Rechazado");
    }
}
