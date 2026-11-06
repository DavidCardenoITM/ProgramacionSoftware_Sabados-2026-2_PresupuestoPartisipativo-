package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.EntidadReceptoraRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntidadReceptoraServiceTest {

    static class FakeEntidadReceptoraRepository
            implements EntidadReceptoraRepositoryInterface {

        List<EntidadReceptora> entidades = new ArrayList<>();

        Integer idRecibido;
        EntidadReceptora entidadRecibida;

        Boolean resultadoEliminar = true;

        @Override
        public List<EntidadReceptora> listarEntidad() {
            return entidades;
        }

        @Override
        public EntidadReceptora listarPorId(Integer id) {
            idRecibido = id;
            return null;
        }

        @Override
        public EntidadReceptora insertarEntidad(
                EntidadReceptora entidad) {
            entidadRecibida = entidad;
            return entidad;
        }

        @Override
        public EntidadReceptora actualizarEntidad(
                EntidadReceptora entidad) {
            entidadRecibida = entidad;
            return entidad;
        }

        @Override
        public Boolean eliminarEntidad(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaEntidadDebeRetornarLista() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        assertNotNull(service.listaEntidad());
        assertTrue(service.listaEntidad().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void insertarEntidadDebeDelegarAlRepositorio() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        assertNull(service.insertarEntidad(null));
        assertNull(repository.entidadRecibida);
    }

    @Test
    void actualizarEntidadDebeDelegarAlRepositorio() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        assertNull(service.actualizarEntidad(null));
        assertNull(repository.entidadRecibida);
    }

    @Test
    void eliminarEntidadDebeRetornarTrue() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        assertTrue(service.eliminarEntidad(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarEntidadDebeRetornarFalse() {
        FakeEntidadReceptoraRepository repository =
                new FakeEntidadReceptoraRepository();

        repository.resultadoEliminar = false;

        EntidadReceptoraService service =
                new EntidadReceptoraService(repository);

        assertFalse(service.eliminarEntidad(1));
    }
}
