package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.ExperienciaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperienciaServiceTest {

    static class FakeExperienciaRepository
            implements ExperienciaRepositoryInterface {

        List<Experiencia> experiencias = new ArrayList<>();

        Integer idRecibido;
        Integer idHojaDeVidaRecibido;

        Experiencia experienciaBuscada;
        Experiencia experienciaRecibida;

        @Override
        public List<Experiencia> listarExperiencia() {
            return experiencias;
        }

        @Override
        public Experiencia listarPorId(Integer id) {
            idRecibido = id;
            return experienciaBuscada;
        }

        @Override
        public List<Experiencia> listarPorHojaDeVida(
                Integer idHojaDeVida) {
            idHojaDeVidaRecibido = idHojaDeVida;
            return experiencias;
        }

        @Override
        public Experiencia insertarExperiencia(
                Experiencia experiencia) {
            experienciaRecibida = experiencia;
            return experiencia;
        }
    }

    @Test
    void listaExperienciaDebeRetornarLista() {
        FakeExperienciaRepository repository =
                new FakeExperienciaRepository();

        ExperienciaService service = new ExperienciaService(repository);

        assertNotNull(service.listaExperiencia());
        assertTrue(service.listaExperiencia().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeExperienciaRepository repository =
                new FakeExperienciaRepository();

        ExperienciaService service = new ExperienciaService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void listarPorHojaDeVidaDebeConsultarIdCorrecto() {
        FakeExperienciaRepository repository =
                new FakeExperienciaRepository();

        ExperienciaService service = new ExperienciaService(repository);

        service.listarPorHojaDeVida(5);

        assertEquals(5, repository.idHojaDeVidaRecibido);
        assertNotNull(service.listarPorHojaDeVida(5));
    }

    @Test
    void insertarExperienciaDebeDelegarAlRepositorio() {
        FakeExperienciaRepository repository =
                new FakeExperienciaRepository();

        ExperienciaService service = new ExperienciaService(repository);

        assertNull(service.insertarExperiencia(null));
        assertNull(repository.experienciaRecibida);
    }
}
