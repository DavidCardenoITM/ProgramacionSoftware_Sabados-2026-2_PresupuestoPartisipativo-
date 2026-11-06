package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.ConvocatoriaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvocatoriaServiceTest {

    static class FakeConvocatoriaRepository
            implements ConvocatoriaRepositoryInterface {

        List<Convocatoria> convocatorias = new ArrayList<>();

        Integer idRecibido;
        Convocatoria convocatoriaRecibida;

        Boolean resultadoEliminar = true;

        @Override
        public List<Convocatoria> listarConvocatoria() {
            return convocatorias;
        }

        @Override
        public Convocatoria listarPorId(Integer id) {
            idRecibido = id;
            return null;
        }

        @Override
        public Convocatoria insertarConvocatoria(
                Convocatoria convocatoria) {
            convocatoriaRecibida = convocatoria;
            return convocatoria;
        }

        @Override
        public Convocatoria actualizarConvocatoria(
                Convocatoria convocatoria) {
            convocatoriaRecibida = convocatoria;
            return convocatoria;
        }

        @Override
        public Boolean eliminarConvocatoria(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaConvocatoriaDebeRetornarLista() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        assertNotNull(service.listaConvocatoria());
        assertTrue(service.listaConvocatoria().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void insertarConvocatoriaDebeDelegarAlRepositorio() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        assertNull(service.insertarConvocatoria(null));
        assertNull(repository.convocatoriaRecibida);
    }

    @Test
    void actualizarConvocatoriaDebeDelegarAlRepositorio() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        assertNull(service.actualizarConvocatoria(null));
        assertNull(repository.convocatoriaRecibida);
    }

    @Test
    void eliminarConvocatoriaDebeRetornarTrue() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        assertTrue(service.eliminarConvocatoria(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarConvocatoriaDebeRetornarFalse() {
        FakeConvocatoriaRepository repository =
                new FakeConvocatoriaRepository();

        repository.resultadoEliminar = false;

        ConvocatoriaService service =
                new ConvocatoriaService(repository);

        assertFalse(service.eliminarConvocatoria(1));
    }
}
