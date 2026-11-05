package com.itm.edu.co.ProyectoPP_2026.Services.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.ActualizarMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.CrearMunicipioRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.MunicipioDtos.MunicipioResumen;
import com.itm.edu.co.ProyectoPP_2026.Exceptions.ApiException;
import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Jpa.MunicipioJpaRepository;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa.MunicipioJpaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MunicipioJpaService implements MunicipioJpaServiceInterface {

    private final MunicipioJpaRepository municipioRepository;
    private final AutorizacionJpaService autorizacion;
    private final AuditoriaJpaService auditoria;

    public MunicipioJpaService(MunicipioJpaRepository municipioRepository,
                               AutorizacionJpaService autorizacion,
                               AuditoriaJpaService auditoria) {
        this.municipioRepository = municipioRepository;
        this.autorizacion = autorizacion;
        this.auditoria = auditoria;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MunicipioResumen> listar() {
        return municipioRepository.findAllByOrderByNombreAsc().stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional
    public MunicipioResumen crear(CrearMunicipioRequest request, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        String nombre = validarTexto(request.nombre(), "nombre");
        String codigoDane = validarTexto(request.codigoDane(), "codigoDane");
        if (municipioRepository.existsByCodigoDane(codigoDane)) {
            throw new ApiException(HttpStatus.CONFLICT, "MUNICIPIO_DUPLICADO",
                    "Ya existe un municipio con el codigo DANE indicado");
        }
        Municipio municipio = new Municipio();
        municipio.setNombre(nombre);
        municipio.setCodigoDane(codigoDane);
        municipioRepository.save(municipio);
        auditoria.registrar(idUsuario, "CREAR_MUNICIPIO", "municipio",
                "Municipio " + nombre + " con codigo DANE " + codigoDane);
        return mapear(municipio);
    }

    @Override
    @Transactional
    public MunicipioResumen actualizar(Integer id, ActualizarMunicipioRequest request, Integer idUsuario) {
        autorizacion.validarAdministrador(idUsuario);
        if (request == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "SOLICITUD_INVALIDA", "La solicitud no puede estar vacia");
        }
        Municipio municipio = buscar(id);
        String nombre = validarTexto(request.nombre(), "nombre");
        String codigoDane = validarTexto(request.codigoDane(), "codigoDane");
        municipioRepository.findByCodigoDane(codigoDane)
                .filter(encontrado -> !encontrado.getId().equals(id))
                .ifPresent(encontrado -> {
                    throw new ApiException(HttpStatus.CONFLICT, "MUNICIPIO_DUPLICADO",
                            "Ya existe un municipio con el codigo DANE indicado");
                });
        municipio.setNombre(nombre);
        municipio.setCodigoDane(codigoDane);
        municipioRepository.save(municipio);
        auditoria.registrar(idUsuario, "ACTUALIZAR_MUNICIPIO", "municipio",
                "Municipio " + id + " actualizado a " + nombre + " con codigo DANE " + codigoDane);
        return mapear(municipio);
    }

    private Municipio buscar(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "MUNICIPIO_INVALIDO", "El id del municipio no es valido");
        }
        return municipioRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "MUNICIPIO_NO_ENCONTRADO",
                        "El municipio indicado no existe"));
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CAMPO_REQUERIDO",
                    "El campo '" + campo + "' es obligatorio");
        }
        return valor.trim();
    }

    private MunicipioResumen mapear(Municipio municipio) {
        return new MunicipioResumen(municipio.getId(), municipio.getNombre(), municipio.getCodigoDane());
    }
}
