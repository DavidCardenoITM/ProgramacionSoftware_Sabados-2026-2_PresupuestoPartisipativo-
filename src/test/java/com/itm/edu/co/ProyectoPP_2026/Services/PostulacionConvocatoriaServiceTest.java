package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.PostulacionConvocatoriaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostulacionConvocatoriaServiceTest {

    static class FakePostulacionConvocatoriaRepository
            implements PostulacionConvocatoriaRepositoryInterface {

        List<PostulacionConvocatoria> postulaciones = new ArrayList<>();

        PostulacionConvocatoria postulacionBuscada;
        PostulacionConvocatoria postulacionInsertada;
        PostulacionConvocatoria postulacionActualizada;

        Integer idRecibido;
        PostulacionConvocatoria postulacionRecibida;

        Boolean resultadoEliminar = true;

        @Override
        public List<PostulacionConvocatoria> listarPostulacion() {
            return postulaciones;
        }

        @Override
        public PostulacionConvocatoria listarPorId(Integer id) {
            idRecibido = id;
            return postulacionBuscada;
        }

        @Override
        public PostulacionConvocatoria insertarPostulacion(PostulacionConvocatoria postulacion) {
            postulacionRecibida = postulacion;
            return postulacionInsertada;
        }

        @Override
        public PostulacionConvocatoria actualizarPostulacion(PostulacionConvocatoria postulacion) {
            postulacionRecibida = postulacion;
            return postulacionActualizada;
        }

        @Override
        public Boolean eliminarPostulacion(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaPostulacionDebeRetornarLista() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        assertNotNull(service.listaPostulacion());
        assertTrue(service.listaPostulacion().isEmpty());
    }

    @Test
    void listarPorIdDebeRetornarPostulacion() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        PostulacionConvocatoria postulacion = repository.postulacionBuscada;

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
        assertSame(postulacion, repository.postulacionBuscada);
    }

    @Test
    void insertarPostulacionDebeDelegarAlRepositorio() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        PostulacionConvocatoria resultado = service.insertarPostulacion(null);

        assertNull(resultado);
        assertNull(repository.postulacionRecibida);
    }

    @Test
    void actualizarPostulacionDebeDelegarAlRepositorio() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        PostulacionConvocatoria resultado = service.actualizarPostulacion(null);

        assertNull(resultado);
        assertNull(repository.postulacionRecibida);
    }

    @Test
    void eliminarPostulacionDebeRetornarTrue() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        assertTrue(service.eliminarPostulacion(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarPostulacionDebeRetornarFalse() {
        FakePostulacionConvocatoriaRepository repository =
                new FakePostulacionConvocatoriaRepository();

        repository.resultadoEliminar = false;

        PostulacionConvocatoriaService service =
                new PostulacionConvocatoriaService(repository);

        assertFalse(service.eliminarPostulacion(1));
    }
}
