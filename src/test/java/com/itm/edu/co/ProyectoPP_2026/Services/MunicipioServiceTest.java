package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.MunicipioRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MunicipioServiceTest {

    static class FakeMunicipioRepository
            implements MunicipioRepositoryInterface {

        List<Municipio> municipios = new ArrayList<>();

        Municipio municipioBuscado;
        Municipio municipioInsertado;
        Municipio municipioActualizado;

        Integer idRecibido;
        Municipio municipioRecibido;

        Boolean resultadoEliminar = true;

        @Override
        public List<Municipio> listarMunicipio() {
            return municipios;
        }

        @Override
        public Municipio listarPorId(Integer id) {
            idRecibido = id;
            return municipioBuscado;
        }

        @Override
        public Municipio insertarMunicipio(Municipio municipio) {
            municipioRecibido = municipio;
            return municipioInsertado;
        }

        @Override
        public Municipio actualizarMunicipio(Municipio municipio) {
            municipioRecibido = municipio;
            return municipioActualizado;
        }

        @Override
        public Boolean eliminarMunicipio(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaMunicipioDebeRetornarLista() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        MunicipioService service =
                new MunicipioService(repository);

        assertNotNull(service.listaMunicipio());
        assertTrue(service.listaMunicipio().isEmpty());
    }

    @Test
    void listarPorIdDebeRetornarMunicipio() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        MunicipioService service =
                new MunicipioService(repository);

        Municipio municipio = repository.municipioBuscado;

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
        assertSame(municipio, repository.municipioBuscado);
    }

    @Test
    void insertarMunicipioDebeDelegarAlRepositorio() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        MunicipioService service =
                new MunicipioService(repository);

        Municipio resultado = service.insertarMunicipio(null);

        assertNull(resultado);
        assertNull(repository.municipioRecibido);
    }

    @Test
    void actualizarMunicipioDebeDelegarAlRepositorio() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        MunicipioService service =
                new MunicipioService(repository);

        Municipio resultado = service.actualizarMunicipio(null);

        assertNull(resultado);
        assertNull(repository.municipioRecibido);
    }

    @Test
    void eliminarMunicipioDebeRetornarTrue() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        MunicipioService service =
                new MunicipioService(repository);

        assertTrue(service.eliminarMunicipio(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarMunicipioDebeRetornarFalse() {
        FakeMunicipioRepository repository =
                new FakeMunicipioRepository();

        repository.resultadoEliminar = false;

        MunicipioService service =
                new MunicipioService(repository);

        assertFalse(service.eliminarMunicipio(1));
    }
}
