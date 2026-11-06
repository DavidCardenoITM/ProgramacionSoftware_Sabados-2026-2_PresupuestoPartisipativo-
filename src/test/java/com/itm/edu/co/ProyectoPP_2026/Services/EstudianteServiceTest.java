package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EstudianteRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteServiceTest {

    static class FakeEstudianteRepository
            implements EstudianteRepositoryInterface {

        List<Estudiante> estudiantes = new ArrayList<>();

        Integer idRecibido;
        Estudiante estudianteRecibido;

        Boolean resultadoEliminar = true;

        @Override
        public List<Estudiante> listarEstudiante() {
            return estudiantes;
        }

        @Override
        public Estudiante listarPorId(Integer id) {
            idRecibido = id;
            return null;
        }

        @Override
        public Estudiante insertarEstudiante(Estudiante estudiante) {
            estudianteRecibido = estudiante;
            return estudiante;
        }

        @Override
        public Estudiante actualizarEstudiante(Estudiante estudiante) {
            estudianteRecibido = estudiante;
            return estudiante;
        }

        @Override
        public Boolean eliminarEstudiante(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaEstudianteDebeRetornarLista() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        EstudianteService service = new EstudianteService(repository);

        assertNotNull(service.listaEstudiante());
        assertTrue(service.listaEstudiante().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        EstudianteService service = new EstudianteService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void insertarEstudianteDebeDelegarAlRepositorio() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        EstudianteService service = new EstudianteService(repository);

        assertNull(service.insertarEstudiante(null));
        assertNull(repository.estudianteRecibido);
    }

    @Test
    void actualizarEstudianteDebeDelegarAlRepositorio() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        EstudianteService service = new EstudianteService(repository);

        assertNull(service.actualizarEstudiante(null));
        assertNull(repository.estudianteRecibido);
    }

    @Test
    void eliminarEstudianteDebeRetornarTrue() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        EstudianteService service = new EstudianteService(repository);

        assertTrue(service.eliminarEstudiante(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarEstudianteDebeRetornarFalse() {
        FakeEstudianteRepository repository =
                new FakeEstudianteRepository();

        repository.resultadoEliminar = false;

        EstudianteService service = new EstudianteService(repository);

        assertFalse(service.eliminarEstudiante(1));
    }
}
