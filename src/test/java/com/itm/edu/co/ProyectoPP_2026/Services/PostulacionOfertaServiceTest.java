package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.OfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.PostulacionOfertaRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostulacionOfertaServiceTest {

    static class FakePostulacionOfertaRepository
            implements PostulacionOfertaRepositoryInterface {

        List<PostulacionOferta> postulaciones = new ArrayList<>();
        PostulacionOferta postulacionBuscada;
        boolean resultadoEliminar = true;

        @Override
        public List<PostulacionOferta> listarPostulaciones() {
            return postulaciones;
        }

        @Override
        public PostulacionOferta buscarPorId(Long id) {
            return postulacionBuscada;
        }

        @Override
        public PostulacionOferta insertarPostulacion(PostulacionOferta postulacion) {
            postulaciones.add(postulacion);
            return postulacion;
        }

        @Override
        public PostulacionOferta actualizarPostulacion(PostulacionOferta postulacion) {
            return postulacion;
        }

        @Override
        public Boolean eliminarPostulacion(Long id) {
            return resultadoEliminar;
        }
    }

    static class FakeOfertaRepository
            implements OfertaRepositoryInterface {

        OfertaLaborSocial ofertaBuscada;

        @Override
        public List<OfertaLaborSocial> listarOfertas() {
            return new ArrayList<>();
        }

        @Override
        public OfertaLaborSocial buscarPorId(Integer id) {
            return ofertaBuscada;
        }

        @Override
        public OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta) {
            return oferta;
        }

        @Override
        public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
            return oferta;
        }

        @Override
        public Boolean eliminarOferta(Integer id) {
            return true;
        }
    }

    private PostulacionOferta crearPostulacion() {
        PostulacionOferta p = new PostulacionOferta();
        p.setIdEstudiante(1L);
        p.setIdOferta(1L);
        p.setEstado("Pendiente");
        p.setFechaPostulacion(LocalDate.now());
        return p;
    }

    private OfertaLaborSocial crearOfertaAbierta() {
        OfertaLaborSocial o = new OfertaLaborSocial();
        o.setId(1);
        o.setIdEntidad(1);
        o.setTitulo("Apoyo");
        o.setEstado("Abierta");
        return o;
    }

    private PostulacionOfertaService crearService(
            FakePostulacionOfertaRepository repo,
            FakeOfertaRepository ofertaRepo) {
        return new PostulacionOfertaService(repo, ofertaRepo);
    }

    @Test
    void listarPostulacionesDebeRetornarLista() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertNotNull(service.listarPostulaciones());
        assertTrue(service.listarPostulaciones().isEmpty());
    }

    @Test
    void buscarPorIdDebeRetornarPostulacion() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOferta p = crearPostulacion();
        p.setId(1L);
        repo.postulacionBuscada = p;
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertEquals(p, service.buscarPorId(1L));
    }

    @Test
    void buscarPorIdDebeRechazarIdInvalido() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(null));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(0L));
    }

    @Test
    void insertarDebeAsignarPendienteYFechaPorDefecto() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        ofertaRepo.ofertaBuscada = crearOfertaAbierta();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);
        PostulacionOferta p = crearPostulacion();
        p.setEstado(null);
        p.setFechaPostulacion(null);

        PostulacionOferta resultado = service.insertarPostulacion(p);

        assertNotNull(resultado);
        assertEquals("Pendiente", p.getEstado());
        assertNotNull(p.getFechaPostulacion());
    }

    @Test
    void insertarDebeRechazarSinEstudiante() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);
        PostulacionOferta p = crearPostulacion();
        p.setIdEstudiante(null);

        assertThrows(IllegalArgumentException.class, () -> service.insertarPostulacion(p));
    }

    @Test
    void insertarDebeRechazarOfertaCerrada() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        OfertaLaborSocial cerrada = crearOfertaAbierta();
        cerrada.setEstado("Cerrada");
        ofertaRepo.ofertaBuscada = cerrada;
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertThrows(IllegalArgumentException.class,
                () -> service.insertarPostulacion(crearPostulacion()));
    }

    @Test
    void insertarDebeRechazarSiOfertaNoExiste() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        ofertaRepo.ofertaBuscada = null;
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertThrows(IllegalArgumentException.class,
                () -> service.insertarPostulacion(crearPostulacion()));
    }

    @Test
    void actualizarDebeRechazarSinId() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);
        PostulacionOferta p = crearPostulacion();

        assertThrows(IllegalArgumentException.class, () -> service.actualizarPostulacion(p));
    }

    @Test
    void actualizarDebeRechazarEstadoInvalido() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);
        PostulacionOferta p = crearPostulacion();
        p.setId(1L);
        p.setEstado("Invalido");

        assertThrows(IllegalArgumentException.class, () -> service.actualizarPostulacion(p));
    }

    @Test
    void eliminarDebeRechazarIdInvalido() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarPostulacion(null));
        assertThrows(IllegalArgumentException.class, () -> service.eliminarPostulacion(0L));
    }

    @Test
    void eliminarDebeRechazarSiNoExiste() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarPostulacion(5L));
    }

    @Test
    void eliminarDebeRetornarTrue() {
        FakePostulacionOfertaRepository repo = new FakePostulacionOfertaRepository();
        FakeOfertaRepository ofertaRepo = new FakeOfertaRepository();
        PostulacionOferta p = crearPostulacion();
        p.setId(1L);
        repo.postulacionBuscada = p;
        PostulacionOfertaService service = crearService(repo, ofertaRepo);

        assertTrue(service.eliminarPostulacion(1L));
    }
}
