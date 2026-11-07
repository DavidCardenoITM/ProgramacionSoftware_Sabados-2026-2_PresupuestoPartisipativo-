package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.RegistroHorasRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistroHorasServiceTest {

    static class FakeRegistroHorasRepository
            implements RegistroHorasRepositoryInterface {

        List<RegistroHoras> registros = new ArrayList<>();
        RegistroHoras registroBuscado;
        boolean resultadoEliminar = true;

        @Override
        public List<RegistroHoras> listarRegistros() {
            return registros;
        }

        @Override
        public RegistroHoras buscarPorId(Long id) {
            return registroBuscado;
        }

        @Override
        public RegistroHoras insertarRegistro(RegistroHoras registro) {
            registros.add(registro);
            return registro;
        }

        @Override
        public RegistroHoras actualizarRegistro(RegistroHoras registro) {
            return registro;
        }

        @Override
        public Boolean eliminarRegistro(Long id) {
            return resultadoEliminar;
        }
    }

    private RegistroHoras crearRegistro() {
        RegistroHoras r = new RegistroHoras();
        r.setIdPostulacionOferta(1L);
        r.setFecha(LocalDate.now());
        r.setHoraInicio("08:00");
        r.setHoraFin("12:00");
        r.setHorasTrabajadas(4.0);
        r.setDescripcionActividad("Apoyo en biblioteca");
        r.setEstadoValidacion("Pendiente");
        return r;
    }

    @Test
    void listarRegistrosDebeRetornarLista() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);

        assertNotNull(service.listarRegistros());
        assertTrue(service.listarRegistros().isEmpty());
    }

    @Test
    void buscarPorIdDebeRetornarRegistro() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHoras registro = crearRegistro();
        registro.setId(1L);
        repository.registroBuscado = registro;
        RegistroHorasService service = new RegistroHorasService(repository);

        assertEquals(registro, service.buscarPorId(1L));
    }

    @Test
    void buscarPorIdDebeRechazarIdInvalido() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(null));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(0L));
    }

    @Test
    void insertarDebeAsignarPendientePorDefecto() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();
        registro.setEstadoValidacion(null);

        RegistroHoras resultado = service.insertarRegistro(registro);

        assertNotNull(resultado);
        assertEquals("Pendiente", registro.getEstadoValidacion());
        assertEquals(1, repository.registros.size());
    }

    @Test
    void insertarDebeRechazarFechaFutura() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();
        registro.setFecha(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> service.insertarRegistro(registro));
    }

    @Test
    void insertarDebeRechazarSiFinNoEsPosterior() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();
        registro.setHoraInicio("12:00");
        registro.setHoraFin("08:00");

        assertThrows(IllegalArgumentException.class, () -> service.insertarRegistro(registro));
    }

    @Test
    void insertarDebeRechazarSiHorasNoCoinciden() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();
        registro.setHorasTrabajadas(10.0);

        assertThrows(IllegalArgumentException.class, () -> service.insertarRegistro(registro));
    }

    @Test
    void insertarDebeRechazarFormatoHoraInvalido() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();
        registro.setHoraInicio("8am");

        assertThrows(IllegalArgumentException.class, () -> service.insertarRegistro(registro));
    }

    @Test
    void actualizarDebeRechazarSinId() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);
        RegistroHoras registro = crearRegistro();

        assertThrows(IllegalArgumentException.class, () -> service.actualizarRegistro(registro));
    }

    @Test
    void eliminarDebeRechazarIdInvalido() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarRegistro(null));
        assertThrows(IllegalArgumentException.class, () -> service.eliminarRegistro(0L));
    }

    @Test
    void eliminarDebeRechazarSiNoExiste() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHorasService service = new RegistroHorasService(repository);

        assertThrows(IllegalArgumentException.class, () -> service.eliminarRegistro(5L));
    }

    @Test
    void eliminarDebeRetornarTrue() {
        FakeRegistroHorasRepository repository = new FakeRegistroHorasRepository();
        RegistroHoras registro = crearRegistro();
        registro.setId(1L);
        repository.registroBuscado = registro;
        RegistroHorasService service = new RegistroHorasService(repository);

        assertTrue(service.eliminarRegistro(1L));
    }
}
