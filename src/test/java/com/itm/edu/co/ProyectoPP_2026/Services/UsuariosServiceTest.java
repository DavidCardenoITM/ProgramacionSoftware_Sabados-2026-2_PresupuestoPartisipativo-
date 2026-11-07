package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Repositories.UsuariosRepository;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosServiceTest {

    // Fake por herencia: UsuariosService pide UsuariosRepository concreto,
    // no la interfaz, entonces extendemos la clase y sobreescribimos
    // para no tocar el Service ni abrir MySQL.
    static class FakeUsuariosRepository extends UsuariosRepository {

        List<Usuarios> usuarios = new ArrayList<>();

        Usuarios usuarioBuscado;
        Usuarios usuarioInsertado;
        Usuarios usuarioActualizado;
        Usuarios usuarioCorregido;

        Integer idRecibido;
        Usuarios usuarioRecibido;
        String correoRecibido;
        String nuevoCorreoRecibido;

        Boolean resultadoEliminar = true;

        @Override
        public List<Usuarios> listarUsuario() {
            return usuarios;
        }

        @Override
        public Usuarios listarPorId(Integer id) {
            idRecibido = id;
            return usuarioBuscado;
        }

        @Override
        public Usuarios insertarUsuario(Usuarios usuario) {
            usuarioRecibido = usuario;
            return usuarioInsertado;
        }

        @Override
        public Usuarios actualizarUsuario(Usuarios usuario) {
            usuarioRecibido = usuario;
            return usuarioActualizado;
        }

        @Override
        public Usuarios corregirCorreo(String correo, String nuevaCorreo) {
            correoRecibido = correo;
            nuevoCorreoRecibido = nuevaCorreo;
            return usuarioCorregido;
        }

        @Override
        public Boolean eliminarUsuario(Integer id) {
            idRecibido = id;
            return resultadoEliminar;
        }
    }

    @Test
    void listaUsuarioDebeRetornarLista() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        assertNotNull(service.listaUsuario());
        assertTrue(service.listaUsuario().isEmpty());
    }

    @Test
    void listarPorIdDebeRetornarUsuario() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        Usuarios usuario = repository.usuarioBuscado;

        service.listarPorId(1);

        assertEquals(1, repository.idRecibido);
        assertSame(usuario, repository.usuarioBuscado);
    }

    @Test
    void insertarUsuarioDebeDelegarAlRepositorio() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        Usuarios resultado = service.insertarUsuario(null);

        assertNull(resultado);
        assertNull(repository.usuarioRecibido);
    }

    @Test
    void actualizarUsuarioDebeDelegarAlRepositorio() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        Usuarios resultado = service.actualizarUsuario(null);

        assertNull(resultado);
        assertNull(repository.usuarioRecibido);
    }

    @Test
    void corregirCorreoDebeEnviarCorreos() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        Usuarios resultado = service.corregirCorreo("a@itm.edu.co", "b@itm.edu.co");

        assertNull(resultado);
        assertEquals("a@itm.edu.co", repository.correoRecibido);
        assertEquals("b@itm.edu.co", repository.nuevoCorreoRecibido);
    }

    @Test
    void eliminarUsuarioDebeRetornarTrue() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        UsuariosService service =
                new UsuariosService(repository);

        assertTrue(service.eliminarUsuario(1));
        assertEquals(1, repository.idRecibido);
    }

    @Test
    void eliminarUsuarioDebeRetornarFalse() {
        FakeUsuariosRepository repository =
                new FakeUsuariosRepository();

        repository.resultadoEliminar = false;

        UsuariosService service =
                new UsuariosService(repository);

        assertFalse(service.eliminarUsuario(1));
    }
}
