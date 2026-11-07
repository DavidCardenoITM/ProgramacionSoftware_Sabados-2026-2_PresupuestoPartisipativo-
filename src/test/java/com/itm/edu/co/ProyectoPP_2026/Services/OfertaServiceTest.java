package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.OfertaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OfertaServiceTest {

    // Misma base del compañero (Fake manual), pero con validaciones
    // estilo CertificadoServiceTest porque OfertaService valida.
    static class FakeOfertaRepository
            implements OfertaRepositoryInterface {

        List<OfertaLaborSocial> ofertas = new ArrayList<>();

        OfertaLaborSocial ofertaBuscada;

        boolean resultadoEliminar = true;

        @Override
        public List<OfertaLaborSocial> listarOfertas() {
            return ofertas;
        }

        @Override
        public OfertaLaborSocial buscarPorId(Integer id) {
            return ofertaBuscada;
        }

        @Override
        public OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta) {
            ofertas.add(oferta);
            return oferta;
        }

        @Override
        public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
            return oferta;
        }

        @Override
        public Boolean eliminarOferta(Integer id) {
            return resultadoEliminar;
        }
    }

    private OfertaLaborSocial crearOferta() {
        OfertaLaborSocial oferta = new OfertaLaborSocial();
        oferta.setIdEntidad(1);
        oferta.setTitulo("Apoyo biblioteca");
        oferta.setDescripcion("Ordenar libros");
        oferta.setCuposDisponibles(10);
        oferta.setHorasOfrecidas(20);
        oferta.setFechaInicio(LocalDate.now());
        oferta.setFechaFin(LocalDate.now().plusDays(30));
        oferta.setEstado("Abierta");
        return oferta;
    }

    @Test
    void listarOfertasDebeRetornarLista() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);

        assertNotNull(service.listarOfertas());
        assertTrue(service.listarOfertas().isEmpty());
    }

    @Test
    void buscarPorIdDebeRetornarOferta() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaLaborSocial oferta = crearOferta();
        oferta.setId(1);
        repository.ofertaBuscada = oferta;
        OfertaService service = new OfertaService(repository);

        assertEquals(oferta, service.buscarPorId(1));
    }

    @Test
    void buscarPorIdDebeRechazarIdInvalido() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(null));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(0));
    }

    @Test
    void insertarOfertaDebeAsignarAbiertaPorDefecto() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);
        OfertaLaborSocial oferta = crearOferta();
        oferta.setEstado(null);

        OfertaLaborSocial resultado = service.insertarOferta(oferta);

        assertNotNull(resultado);
        assertEquals("Abierta", oferta.getEstado());
        assertEquals(1, repository.ofertas.size());
    }

    @Test
    void insertarOfertaDebeRechazarOfertaNula() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.insertarOferta(null));
    }

    @Test
    void insertarOfertaDebeRechazarSinTitulo() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);
        OfertaLaborSocial oferta = crearOferta();
        oferta.setTitulo(" ");

        assertThrows(IllegalArgumentException.class, () -> service.insertarOferta(oferta));
    }

    @Test
    void insertarOfertaDebeRechazarEstadoInvalido() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);
        OfertaLaborSocial oferta = crearOferta();
        oferta.setEstado("Invalido");

        assertThrows(IllegalArgumentException.class, () -> service.insertarOferta(oferta));
    }

    @Test
    void insertarOfertaDebeRechazarFechasInvertidas() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);
        OfertaLaborSocial oferta = crearOferta();
        oferta.setFechaInicio(LocalDate.now().plusDays(10));
        oferta.setFechaFin(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> service.insertarOferta(oferta));
    }

    @Test
    void actualizarOfertaDebeRechazarSinId() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);
        OfertaLaborSocial oferta = crearOferta();

        assertThrows(IllegalArgumentException.class, () -> service.actualizarOferta(oferta));
    }

    @Test
    void eliminarOfertaDebeRechazarIdInvalido() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarOferta(null));
        assertThrows(IllegalArgumentException.class, () -> service.eliminarOferta(0));
    }

    @Test
    void eliminarOfertaDebeRechazarSiNoExiste() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaService service = new OfertaService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarOferta(5));
    }

    @Test
    void eliminarOfertaDebeRetornarTrue() {
        FakeOfertaRepository repository = new FakeOfertaRepository();
        OfertaLaborSocial oferta = crearOferta();
        oferta.setId(1);
        repository.ofertaBuscada = oferta;
        OfertaService service = new OfertaService(repository);

        assertTrue(service.eliminarOferta(1));
    }
}
