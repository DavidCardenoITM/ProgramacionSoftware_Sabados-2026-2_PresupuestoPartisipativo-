package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;

import java.util.List;

public interface CertificadoRepositoryInterface {
    List<Certificado> listarCertificados();
    Certificado buscarPorId(Long id);
    Certificado buscarPorCodigo(String codigoVerificacion);
    Certificado insertarCertificado(Certificado certificado);
    Certificado actualizarCertificado(Certificado certificado);
    Boolean eliminarCertificado(Long id);
}
