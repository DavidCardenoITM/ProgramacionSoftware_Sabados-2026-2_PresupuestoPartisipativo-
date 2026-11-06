package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.NotificacionRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class

NotificacionServiceTest {

    static class FakeNotificacionRepository
            implements NotificacionRepositoryInterface {

        List<Notificacion> notificaciones = new ArrayList<>();

        Notificacion notificacionBuscada;
        Notificacion notificacionInsertada;
        Notificacion notificacionActualizada;

        Integer idRecibido;
        Boolean leidoRecibido;

        Boolean resultadoMarcarLeido = true;
        Boolean resultadoEliminar = true;

        @Override
        public List<Notificacion> listarNotificacion() {
            return notificaciones;
        }

        @Override
        public Notificacion listarPorId(Integer id) {
            idRecibido = id;
            return notificacionBuscada;
        }

        @Override
        public Notificacion insertarNotificacion(Notificacion notificacion) {
            return notificacionInsertada;
        }

        @Override
        public Notificacion actualizarNotificacion(Notificacion notificacion) {
            return notificacionActualizada;
        }

        @Override
        public Boolean marcarLeido(Integer id, Boolean leido) {
            idRecibido = id;
            leidoRecibido = leido;
            return resultadoMarcarLeido;
        }

        @Override
        public Boolean eliminarNotificacion(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaNotificacionDebeRetornarLista() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        assertNotNull(service.listaNotificacion());
        assertTrue(service.listaNotificacion().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void insertarNotificacionDebeDelegarAlRepositorio() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        Notificacion resultado = service.insertarNotificacion(null);

        assertEquals(repository.notificacionInsertada, resultado);
    }

    @Test
    void actualizarNotificacionDebeDelegarAlRepositorio() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        Notificacion resultado = service.actualizarNotificacion(null);

        assertEquals(repository.notificacionActualizada, resultado);
    }

    @Test
    void marcarLeidoDebeRetornarTrue() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        assertTrue(service.marcarLeido(1, true));

        assertEquals(1, repository.idRecibido);
        assertTrue(repository.leidoRecibido);
    }

    @Test
    void marcarLeidoDebeRetornarFalse() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        repository.resultadoMarcarLeido = false;

        NotificacionService service =
                new NotificacionService(repository);

        assertFalse(service.marcarLeido(1, false));

        assertEquals(1, repository.idRecibido);
        assertFalse(repository.leidoRecibido);
    }

    @Test
    void eliminarNotificacionDebeRetornarTrue() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        NotificacionService service =
                new NotificacionService(repository);

        assertTrue(service.eliminarNotificacion(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarNotificacionDebeRetornarFalse() {
        FakeNotificacionRepository repository =
                new FakeNotificacionRepository();

        repository.resultadoEliminar = false;

        NotificacionService service =
                new NotificacionService(repository);

        assertFalse(service.eliminarNotificacion(1));
    }
}
