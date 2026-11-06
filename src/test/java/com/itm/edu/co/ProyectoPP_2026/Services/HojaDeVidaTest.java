package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.HojaDeVidaRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HojaDeVidaServiceTest {

    private HojaDeVidaService service;
    private HojaDeVidaRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new HojaDeVidaRepositoryFake();
        service = new HojaDeVidaService(repository);
    }

    @Test
    void listaHojaDeVidaDebeConsultarRepositorio() {
        List<HojaDeVida> resultado = service.listaHojaDeVida();

        assertSame(repository.hojas, resultado);
    }

    @Test
    void listarPorIdDebeEnviarIdCorrecto() {
        Integer id = 5;

        HojaDeVida resultado = service.listarPorId(id);

        assertEquals(id, repository.idConsultado);
        assertSame(repository.hojaBuscada, resultado);
    }

    @Test
    void listarPorEstudianteDebeEnviarIdCorrecto() {
        Integer idEstudiante = 10;

        HojaDeVida resultado = service.listarPorEstudiante(idEstudiante);

        assertEquals(idEstudiante, repository.idEstudianteConsultado);
        assertSame(repository.hojaPorEstudiante, resultado);
    }

    @Test
    void insertarHojaDeVidaDebeEnviarHojaAlRepositorio() {
        HojaDeVida hoja = new HojaDeVida();

        HojaDeVida resultado = service.insertarHojaDeVida(hoja);

        assertSame(hoja, repository.hojaRecibida);
        assertSame(hoja, resultado);
    }

    // Repositorio falso: no utiliza conexión a MySQL.
    private static class HojaDeVidaRepositoryFake
            implements HojaDeVidaRepositoryInterface {

        private final List<HojaDeVida> hojas = new ArrayList<>();
        private Integer idConsultado;
        private Integer idEstudianteConsultado;
        private HojaDeVida hojaBuscada;
        private HojaDeVida hojaPorEstudiante;
        private HojaDeVida hojaRecibida;

        @Override
        public List<HojaDeVida> listarHojaDeVida() {
            return hojas;
        }

        @Override
        public HojaDeVida listarPorId(Integer id) {
            idConsultado = id;
            return hojaBuscada;
        }

        @Override
        public HojaDeVida listarPorEstudiante(Integer idEstudiante) {
            idEstudianteConsultado = idEstudiante;
            return hojaPorEstudiante;
        }

        @Override
        public HojaDeVida insertarHojaDeVida(HojaDeVida hoja) {
            hojaRecibida = hoja;
            return hoja;
        }
    }
}
