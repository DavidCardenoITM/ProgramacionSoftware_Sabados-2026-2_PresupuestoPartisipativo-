package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.ValidacionFirmaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionFirmaServiceTest {

    static class FakeValidacionFirmaRepository
            implements ValidacionFirmaRepositoryInterface {

        List<ValidacionFirma> validaciones = new ArrayList<>();

        ValidacionFirma validacionBuscada;
        ValidacionFirma validacionInsertada;

        Integer idRecibido;
        ValidacionFirma validacionRecibida;

        @Override
        public List<ValidacionFirma> listarValidacionFirma() {
            return validaciones;
        }

        @Override
        public ValidacionFirma listarPorId(Integer id) {
            idRecibido = id;
            return validacionBuscada;
        }

        @Override
        public ValidacionFirma insertarValidacion(ValidacionFirma validacion) {
            validacionRecibida = validacion;
            return validacionInsertada;
        }
    }

    @Test
    void listaValidacionFirmaDebeRetornarLista() {
        FakeValidacionFirmaRepository repository =
                new FakeValidacionFirmaRepository();

        ValidacionFirmaService service =
                new ValidacionFirmaService(repository);

        assertNotNull(service.listaValidacionFirma());
        assertTrue(service.listaValidacionFirma().isEmpty());
    }

    @Test
    void listarPorIdDebeRetornarValidacion() {
        FakeValidacionFirmaRepository repository =
                new FakeValidacionFirmaRepository();

        ValidacionFirmaService service =
                new ValidacionFirmaService(repository);

        ValidacionFirma validacion = repository.validacionBuscada;

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
        assertSame(validacion, repository.validacionBuscada);
    }

    @Test
    void insertarValidacionDebeDelegarAlRepositorio() {
        FakeValidacionFirmaRepository repository =
                new FakeValidacionFirmaRepository();

        ValidacionFirmaService service =
                new ValidacionFirmaService(repository);

        ValidacionFirma resultado = service.insertarValidacion(null);

        assertNull(resultado);
        assertNull(repository.validacionRecibida);
    }
}
