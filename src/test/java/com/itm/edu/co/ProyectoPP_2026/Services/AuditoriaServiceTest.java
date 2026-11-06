package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.AuditoriaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditoriaServiceTest {

    static class FakeAuditoriaRepository
            implements AuditoriaRepositoryInterface {

        List<Auditoria> auditorias = new ArrayList<>();
        Auditoria auditoriaBuscada;
        Auditoria auditoriaInsertada;
        Integer idRecibido;
        Auditoria auditoriaRecibida;

        @Override
        public List<Auditoria> listarAuditoria() {
            return auditorias;
        }

        @Override
        public Auditoria listarPorId(Integer id) {
            idRecibido = id;
            return auditoriaBuscada;
        }

        @Override
        public Auditoria insertarAuditoria(Auditoria auditoria) {
            auditoriaRecibida = auditoria;
            return auditoriaInsertada;
        }
    }

    @Test
    void listaAuditoriaDebeRetornarLista() {
        FakeAuditoriaRepository repository =
                new FakeAuditoriaRepository();

        AuditoriaService service = new AuditoriaService(repository);

        assertNotNull(service.listaAuditoria());
        assertTrue(service.listaAuditoria().isEmpty());
    }

    @Test
    void listarPorIdDebeConsultarElIdCorrecto() {
        FakeAuditoriaRepository repository =
                new FakeAuditoriaRepository();

        AuditoriaService service = new AuditoriaService(repository);

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
    }

    @Test
    void insertarAuditoriaDebeDelegarAlRepositorio() {
        FakeAuditoriaRepository repository =
                new FakeAuditoriaRepository();

        AuditoriaService service = new AuditoriaService(repository);

        service.insertarAuditoria(null);

        assertNull(repository.auditoriaRecibida);
        assertNull(repository.auditoriaInsertada);
    }
}

