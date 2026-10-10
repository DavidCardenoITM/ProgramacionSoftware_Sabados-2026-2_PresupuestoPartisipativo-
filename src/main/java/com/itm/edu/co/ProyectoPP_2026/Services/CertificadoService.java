package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.CertificadoRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.CertificadoServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificadoService implements CertificadoServiceInterface {

    private final CertificadoRepositoryInterface repository;

    public CertificadoService(CertificadoRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public List<Certificado> listarCertificados() {
        return repository.listarCertificados();
    }

    @Override
    public Certificado buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del certificado es invalido");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public Certificado insertarCertificado(Certificado certificado) {
        validarCertificado(certificado);
        if (certificado.getFechaEmision() == null) {
            certificado.setFechaEmision(LocalDate.now());
        }
        if (certificado.getCodigoVerificacion() == null || certificado.getCodigoVerificacion().isBlank()) {
            certificado.setCodigoVerificacion(UUID.randomUUID().toString());
        }
        validarCodigoUnico(certificado.getCodigoVerificacion(), null);
        return repository.insertarCertificado(certificado);
    }

    @Override
    public Certificado actualizarCertificado(Certificado certificado) {
        if (certificado == null || certificado.getId() == null) {
            throw new IllegalArgumentException("El certificado y su id son obligatorios para actualizar");
        }
        validarCertificado(certificado);
        if (certificado.getCodigoVerificacion() != null) {
            validarCodigoUnico(certificado.getCodigoVerificacion(), certificado.getId());
        }
        return repository.actualizarCertificado(certificado);
    }

    @Override
    public Boolean eliminarCertificado(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del certificado es invalido");
        }
        Certificado actual = repository.buscarPorId(id);
        if (actual == null || actual.getId() == null) {
            throw new IllegalArgumentException("El certificado con id " + id + " no existe");
        }
        return repository.eliminarCertificado(id);
    }

    private void validarCertificado(Certificado certificado) {
        if (certificado == null) {
            throw new IllegalArgumentException("El certificado es obligatorio");
        }
        if (certificado.getIdEstudiante() == null) {
            throw new IllegalArgumentException("El idEstudiante es obligatorio");
        }
        if (certificado.getHorasTotales() == null || certificado.getHorasTotales() <= 0) {
            throw new IllegalArgumentException("Las horasTotales deben ser mayores a cero");
        }
    }

    private void validarCodigoUnico(String codigo, Long idActual) {
        Certificado existente = repository.buscarPorCodigo(codigo);
        if (existente != null && existente.getId() != null) {
            if (idActual == null || !existente.getId().equals(idActual)) {
                throw new IllegalArgumentException("El codigoVerificacion ya existe");
            }
        }
    }
}
