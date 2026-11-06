package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.CertificadoRepositoryInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoServiceTest {

    // Repositorio falso para evitar conexiones con la base de datos
    static class FakeCertificadoRepository
            implements CertificadoRepositoryInterface {

        List<Certificado> certificados = new ArrayList<>();

        Certificado certificadoBuscado;
        Certificado certificadoPorCodigo;

        boolean resultadoEliminar = true;

        @Override
        public List<Certificado> listarCertificados() {
            return certificados;
        }

        @Override
        public Certificado buscarPorId(Long id) {
            return certificadoBuscado;
        }

        @Override
        public Certificado buscarPorCodigo(String codigoVerificacion) {
            return certificadoPorCodigo;
        }

        @Override
        public Certificado insertarCertificado(Certificado certificado) {
            certificados.add(certificado);
            return certificado;
        }

        @Override
        public Certificado actualizarCertificado(Certificado certificado) {
            return certificado;
        }

        @Override
        public Boolean eliminarCertificado(Long id) {
            return resultadoEliminar;
        }
    }

    // Crea un certificado válido para las pruebas
    private Certificado crearCertificado() {
        Certificado certificado = new Certificado();

        certificado.setIdEstudiante(1L);
        certificado.setHorasTotales(40);

        return certificado;
    }

    @Test
    void listarCertificadosDebeRetornarLista() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        assertNotNull(service.listarCertificados());
        assertTrue(service.listarCertificados().isEmpty());
    }

    @Test
    void buscarPorIdDebeRetornarCertificado() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        Certificado certificado = crearCertificado();
        certificado.setId(1L);

        repository.certificadoBuscado = certificado;

        CertificadoService service =
                new CertificadoService(repository);

        assertEquals(certificado, service.buscarPorId(1L));
    }

    @Test
    void buscarPorIdDebeRechazarIdInvalido() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.buscarPorId(null));

        assertThrows(IllegalArgumentException.class,
                () -> service.buscarPorId(0L));
    }

    @Test
    void insertarCertificadoDebeGenerarFechaYCodigo() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        Certificado certificado = crearCertificado();

        Certificado resultado =
                service.insertarCertificado(certificado);

        assertNotNull(resultado);
        assertNotNull(certificado.getFechaEmision());
        assertNotNull(certificado.getCodigoVerificacion());
        assertFalse(certificado.getCodigoVerificacion().isBlank());

        assertEquals(1, repository.certificados.size());
    }

    @Test
    void insertarCertificadoDebeRechazarCertificadoNulo() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.insertarCertificado(null));
    }

    @Test
    void insertarCertificadoDebeRechazarHorasInvalidas() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        Certificado certificado = crearCertificado();
        certificado.setHorasTotales(0);

        assertThrows(IllegalArgumentException.class,
                () -> service.insertarCertificado(certificado));
    }

    @Test
    void actualizarCertificadoDebeRechazarIdNulo() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        Certificado certificado = crearCertificado();

        assertThrows(IllegalArgumentException.class,
                () -> service.actualizarCertificado(certificado));
    }

    @Test
    void actualizarCertificadoDebeRetornarCertificado() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        Certificado certificado = crearCertificado();
        certificado.setId(1L);

        Certificado resultado =
                service.actualizarCertificado(certificado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void eliminarCertificadoDebeRechazarIdInvalido() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.eliminarCertificado(null));

        assertThrows(IllegalArgumentException.class,
                () -> service.eliminarCertificado(0L));
    }

    @Test
    void eliminarCertificadoDebeRetornarTrue() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        Certificado certificado = crearCertificado();
        certificado.setId(1L);

        repository.certificadoBuscado = certificado;

        CertificadoService service =
                new CertificadoService(repository);

        assertTrue(service.eliminarCertificado(1L));
    }

    @Test
    void eliminarCertificadoDebeRechazarCertificadoInexistente() {
        FakeCertificadoRepository repository =
                new FakeCertificadoRepository();

        CertificadoService service =
                new CertificadoService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.eliminarCertificado(5L));
    }
}

