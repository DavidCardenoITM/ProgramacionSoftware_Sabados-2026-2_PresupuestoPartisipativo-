package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.RepresentanteEntidadRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepresentanteEntidadServiceTest {

    static class FakeRepresentanteEntidadRepository
            implements RepresentanteEntidadRepositoryInterface {

        List<RepresentanteEntidad> representantes = new ArrayList<>();

        RepresentanteEntidad representanteBuscado;
        RepresentanteEntidad representanteInsertado;
        RepresentanteEntidad representanteActualizado;

        Integer idRecibido;
        RepresentanteEntidad representanteRecibido;

        Boolean resultadoEliminar = true;

        @Override
        public List<RepresentanteEntidad> listarRepresentante() {
            return representantes;
        }

        @Override
        public RepresentanteEntidad listarPorId(Integer id) {
            idRecibido = id;
            return representanteBuscado;
        }

        @Override
        public RepresentanteEntidad insertarRepresentante(RepresentanteEntidad representante) {
            representanteRecibido = representante;
            return representanteInsertado;
        }

        @Override
        public RepresentanteEntidad actualizarRepresentante(RepresentanteEntidad representante) {
            representanteRecibido = representante;
            return representanteActualizado;
        }

        @Override
        public Boolean eliminarRepresentante(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaRepresentanteDebeRetornarLista() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        assertNotNull(service.listaRepresentante());
        assertTrue(service.listaRepresentante().isEmpty());
    }

    @Test
    void listarPorIdDebeRetornarRepresentante() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        RepresentanteEntidad representante = repository.representanteBuscado;

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
        assertSame(representante, repository.representanteBuscado);
    }

    @Test
    void insertarRepresentanteDebeDelegarAlRepositorio() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        RepresentanteEntidad resultado = service.insertarRepresentante(null);

        assertNull(resultado);
        assertNull(repository.representanteRecibido);
    }

    @Test
    void actualizarRepresentanteDebeDelegarAlRepositorio() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        RepresentanteEntidad resultado = service.actualizarRepresentante(null);

        assertNull(resultado);
        assertNull(repository.representanteRecibido);
    }

    @Test
    void eliminarRepresentanteDebeRetornarTrue() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        assertTrue(service.eliminarRepresentante(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarRepresentanteDebeRetornarFalse() {
        FakeRepresentanteEntidadRepository repository =
                new FakeRepresentanteEntidadRepository();

        repository.resultadoEliminar = false;

        RepresentanteEntidadService service =
                new RepresentanteEntidadService(repository);

        assertFalse(service.eliminarRepresentante(1));
    }
}
