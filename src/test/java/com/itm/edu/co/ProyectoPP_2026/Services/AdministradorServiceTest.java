package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.AdministradorRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorServiceTest {

    private AdministradorService service;
    private AdministradorRepositoryFake repository;

    @BeforeEach
    void setUp() {
        repository = new AdministradorRepositoryFake();
        service = new AdministradorService(repository);
    }

    @Test
    void listaAdministradorDebeConsultarRepositorio() {
        List<Administrador> resultado = service.listaAdministrador();

        assertSame(repository.administradores, resultado);
    }

    @Test
    void listarPorIdDebeEnviarIdCorrecto() {
        Integer id = 5;

        Administrador resultado = service.listarPorId(id);

        assertEquals(id, repository.idConsultado);
        assertSame(repository.administradorBuscado, resultado);
    }

    @Test
    void insertarAdministradorDebeEnviarAdministradorAlRepositorio() {
        Administrador administrador = new Administrador();

        Administrador resultado = service.insertarAdministrador(administrador);

        assertSame(administrador, repository.administradorRecibido);
        assertSame(administrador, resultado);
    }

    @Test
    void actualizarAdministradorDebeEnviarAdministradorAlRepositorio() {
        Administrador administrador = new Administrador();

        Administrador resultado = service.actualizarAdministrador(administrador);

        assertSame(administrador, repository.administradorRecibido);
        assertSame(administrador, resultado);
    }

    @Test
    void eliminarAdministradorDebeEnviarIdCorrecto() {
        Integer id = 5;

        Boolean resultado = service.eliminarAdministrador(id);

        assertEquals(id, repository.idEliminado);
        assertTrue(resultado);
    }

    // Repositorio falso: no utiliza conexión a MySQL.
    private static class AdministradorRepositoryFake
            implements AdministradorRepositoryInterface {

        private final List<Administrador> administradores = new ArrayList<>();
        private Integer idConsultado;
        private Integer idEliminado;
        private Administrador administradorBuscado;
        private Administrador administradorRecibido;

        @Override
        public List<Administrador> listarAdministrador() {
            return administradores;
        }

        @Override
        public Administrador listarPorId(Integer id) {
            idConsultado = id;
            return administradorBuscado;
        }

        @Override
        public Administrador insertarAdministrador(Administrador administrador) {
            administradorRecibido = administrador;
            return administrador;
        }

        @Override
        public Administrador actualizarAdministrador(Administrador administrador) {
            administradorRecibido = administrador;
            return administrador;
        }

        @Override
        public Boolean eliminarAdministrador(Integer id) {
            idEliminado = id;
            return true;
        }
    }
}