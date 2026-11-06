package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EstudianteHabilidadRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteHabilidadServiceTest {

    static class FakeEstudianteHabilidadRepository
            implements EstudianteHabilidadRepositoryInterface {

        List<EstudianteHabilidad> relaciones = new ArrayList<>();

        Integer idEstudianteRecibido;
        Integer idHabilidadRecibido;

        EstudianteHabilidad relacionRecibida;

        boolean resultadoEliminar = true;

        @Override
        public List<EstudianteHabilidad> listarEstudianteHabilidad() {
            return relaciones;
        }

        @Override
        public List<EstudianteHabilidad> listarPorEstudiante(
                Integer idEstudiante) {
            idEstudianteRecibido = idEstudiante;
            return relaciones;
        }

        @Override
        public EstudianteHabilidad insertarEstudianteHabilidad(
                EstudianteHabilidad eh) {
            relacionRecibida = eh;
            return eh;
        }

        @Override
        public boolean eliminarEstudianteHabilidad(
                Integer idEstudiante, Integer idHabilidad) {
            idEstudianteRecibido = idEstudiante;
            idHabilidadRecibido = idHabilidad;
            return resultadoEliminar;
        }
    }

    @Test
    void listaEstudianteHabilidadDebeRetornarLista() {
        FakeEstudianteHabilidadRepository repository =
                new FakeEstudianteHabilidadRepository();

        EstudianteHabilidadService service =
                new EstudianteHabilidadService(repository);

        assertNotNull(service.listaEstudianteHabilidad());
        assertTrue(service.listaEstudianteHabilidad().isEmpty());
    }

    @Test
    void listarPorEstudianteDebeConsultarIdCorrecto() {
        FakeEstudianteHabilidadRepository repository =
                new FakeEstudianteHabilidadRepository();

        EstudianteHabilidadService service =
                new EstudianteHabilidadService(repository);

        service.listarPorEstudiante(5);

        assertEquals(5, repository.idEstudianteRecibido);
    }

    @Test
    void insertarEstudianteHabilidadDebeDelegarAlRepositorio() {
        FakeEstudianteHabilidadRepository repository =
                new FakeEstudianteHabilidadRepository();

        EstudianteHabilidadService service =
                new EstudianteHabilidadService(repository);

        assertNull(service.insertarEstudianteHabilidad(null));
        assertNull(repository.relacionRecibida);
    }

    @Test
    void eliminarEstudianteHabilidadDebeRetornarTrue() {
        FakeEstudianteHabilidadRepository repository =
                new FakeEstudianteHabilidadRepository();

        EstudianteHabilidadService service =
                new EstudianteHabilidadService(repository);

        assertTrue(service.eliminarEstudianteHabilidad(1, 2));

        assertEquals(1, repository.idEstudianteRecibido);
        assertEquals(2, repository.idHabilidadRecibido);
    }

    @Test
    void eliminarEstudianteHabilidadDebeRetornarFalse() {
        FakeEstudianteHabilidadRepository repository =
                new FakeEstudianteHabilidadRepository();

        repository.resultadoEliminar = false;

        EstudianteHabilidadService service =
                new EstudianteHabilidadService(repository);

        assertFalse(service.eliminarEstudianteHabilidad(1, 2));
    }
}
