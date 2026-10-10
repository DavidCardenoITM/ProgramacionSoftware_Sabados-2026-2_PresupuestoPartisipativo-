package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.OfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.OfertaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService implements OfertaServiceInterface {

    private final OfertaRepositoryInterface repository;

    public OfertaService(OfertaRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public List<OfertaLaborSocial> listarOfertas() {
        return repository.listarOfertas();
    }

    @Override
    public OfertaLaborSocial buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la oferta es invalido");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta) {
        validarOferta(oferta, false);
        if (oferta.getEstado() == null || oferta.getEstado().isBlank()) {
            oferta.setEstado("Abierta");
        }
        return repository.insertarOferta(oferta);
    }

    @Override
    public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
        if (oferta == null || oferta.getId() == null) {
            throw new IllegalArgumentException("La oferta y su id son obligatorios para actualizar");
        }
        validarOferta(oferta, true);
        return repository.actualizarOferta(oferta);
    }

    @Override
    public Boolean eliminarOferta(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la oferta es invalido");
        }
        OfertaLaborSocial actual = repository.buscarPorId(id);
        if (actual == null || actual.getId() == null) {
            throw new IllegalArgumentException("La oferta con id " + id + " no existe");
        }
        return repository.eliminarOferta(id);
    }

    private void validarOferta(OfertaLaborSocial oferta, boolean esActualizacion) {
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta es obligatoria");
        }
        if (oferta.getIdEntidad() == null) {
            throw new IllegalArgumentException("El idEntidad es obligatorio");
        }
        if (oferta.getTitulo() == null || oferta.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El titulo es obligatorio");
        }
        if (oferta.getCuposDisponibles() != null && oferta.getCuposDisponibles() < 0) {
            throw new IllegalArgumentException("Los cupos no pueden ser negativos");
        }
        if (oferta.getHorasOfrecidas() != null && oferta.getHorasOfrecidas() <= 0) {
            throw new IllegalArgumentException("Las horas ofrecidas deben ser mayores a cero");
        }
        if (oferta.getEstado() != null && !esEstadoValido(oferta.getEstado())) {
            throw new IllegalArgumentException("Estado invalido. Use: Abierta, Cerrada");
        }
        if (oferta.getFechaInicio() != null && oferta.getFechaFin() != null
                && oferta.getFechaInicio().after(oferta.getFechaFin())) {
            throw new IllegalArgumentException("La fechaInicio no puede ser posterior a fechaFin");
        }
    }

    private boolean esEstadoValido(String estado) {
        return estado.equalsIgnoreCase("Abierta") || estado.equalsIgnoreCase("Cerrada");
    }
}
