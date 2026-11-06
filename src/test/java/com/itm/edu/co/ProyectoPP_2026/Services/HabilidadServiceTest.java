package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.HabilidadRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HabilidadServiceTest {

    private HabilidadService service;
    private HabilidadRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new HabilidadRepositoryFake();
        service = new HabilidadService(repository);
    }

    @Test
    void listaHabilidadDebeConsultarRepositorio() {
        List<Habilidad> resultado = service.listaHabilidad();

        assertSame(repository.habilidades, resultado);
    }

    @Test
    void listarPorIdDebeEnviarIdCorrecto() {
        Integer id = 5;

        Habilidad resultado = service.listarPorId(id);

        assertEquals(id, repository.idConsultado);
        assertSame(repository.habilidadBuscada, resultado);
    }

    @Test
    void insertarHabilidadDebeEnviarHabilidadAlRepositorio() {
        Habilidad habilidad = new Habilidad();

        Habilidad resultado = service.insertarHabilidad(habilidad);

        assertSame(habilidad, repository.habilidadRecibida);
        assertSame(habilidad, resultado);
    }

    // Repositorio falso: no utiliza conexión a MySQL.
    private static class HabilidadRepositoryFake
            implements HabilidadRepositoryInterface {

        private final List<Habilidad> habilidades = new ArrayList<>();
        private Integer idConsultado;
        private Habilidad habilidadBuscada;
        private Habilidad habilidadRecibida;

        @Override
        public List<Habilidad> listarHabilidad() {
            return habilidades;
        }

        @Override
        public Habilidad listarPorId(Integer id) {
            idConsultado = id;
            return habilidadBuscada;
        }

        @Override
        public Habilidad insertarHabilidad(Habilidad habilidad) {
            habilidadRecibida = habilidad;
            return habilidad;
        }
    }
}
