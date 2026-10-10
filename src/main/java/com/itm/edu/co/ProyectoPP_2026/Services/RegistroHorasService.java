package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.RegistroHorasRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.RegistroHorasServiceInterface;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class RegistroHorasService implements RegistroHorasServiceInterface {

    private final RegistroHorasRepositoryInterface repository;

    public RegistroHorasService(RegistroHorasRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public List<RegistroHoras> listarRegistros() {
        return repository.listarRegistros();
    }

    @Override
    public RegistroHoras buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del registro es invalido");
        }
        return repository.buscarPorId(id);
    }

    @Override
    public RegistroHoras insertarRegistro(RegistroHoras registro) {
        validarRegistro(registro);
        if (registro.getEstadoValidacion() == null || registro.getEstadoValidacion().isBlank()) {
            registro.setEstadoValidacion("Pendiente");
        }
        return repository.insertarRegistro(registro);
    }

    @Override
    public RegistroHoras actualizarRegistro(RegistroHoras registro) {
        if (registro == null || registro.getId() == null) {
            throw new IllegalArgumentException("El registro y su id son obligatorios para actualizar");
        }
        validarRegistro(registro);
        return repository.actualizarRegistro(registro);
    }

    @Override
    public Boolean eliminarRegistro(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del registro es invalido");
        }
        RegistroHoras actual = repository.buscarPorId(id);
        if (actual == null || actual.getId() == null) {
            throw new IllegalArgumentException("El registro con id " + id + " no existe");
        }
        return repository.eliminarRegistro(id);
    }

    private void validarRegistro(RegistroHoras registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro es obligatorio");
        }
        if (registro.getIdPostulacionOferta() == null) {
            throw new IllegalArgumentException("El idPostulacionOferta es obligatorio");
        }
        if (registro.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (registro.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        if (registro.getHoraInicio() == null || registro.getHoraInicio().isBlank()) {
            throw new IllegalArgumentException("La horaInicio es obligatoria (formato HH:mm)");
        }
        if (registro.getHoraFin() == null || registro.getHoraFin().isBlank()) {
            throw new IllegalArgumentException("La horaFin es obligatoria (formato HH:mm)");
        }
        if (registro.getHorasTrabajadas() == null) {
            throw new IllegalArgumentException("Las horasTrabajadas son obligatorias");
        }
        if (registro.getDescripcionActividad() == null || registro.getDescripcionActividad().isBlank()) {
            throw new IllegalArgumentException("La descripcionActividad es obligatoria");
        }
        if (registro.getEstadoValidacion() != null && !esEstadoValido(registro.getEstadoValidacion())) {
            throw new IllegalArgumentException("Estado invalido. Use: Pendiente, Aprobado, Rechazado");
        }
        LocalTime inicio = parseHora(registro.getHoraInicio(), "horaInicio");
        LocalTime fin = parseHora(registro.getHoraFin(), "horaFin");
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La horaFin debe ser posterior a horaInicio");
        }
        double calculadas = Duration.between(inicio, fin).toMinutes() / 60.0;
        if (Math.abs(calculadas - registro.getHorasTrabajadas()) > 0.01) {
            throw new IllegalArgumentException("Las horasTrabajadas (" + registro.getHorasTrabajadas()
                    + ") no coinciden con horaInicio/horaFin (" + calculadas + ")");
        }
        if (registro.getHorasTrabajadas() <= 0 || registro.getHorasTrabajadas() > 12) {
            throw new IllegalArgumentException("Las horasTrabajadas deben estar entre 0 y 12");
        }
    }

    private LocalTime parseHora(String valor, String campo) {
        try {
            return LocalTime.parse(valor);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe tener formato HH:mm");
        }
    }

    private boolean esEstadoValido(String estado) {
        return estado.equalsIgnoreCase("Pendiente")
                || estado.equalsIgnoreCase("Aprobado")
                || estado.equalsIgnoreCase("Rechazado");
    }
}
