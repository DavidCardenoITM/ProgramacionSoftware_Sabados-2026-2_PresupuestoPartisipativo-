package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;

import java.util.List;

public interface CertificadoServiceInterface {

    List<Certificado> listarCertificados();

    Certificado buscarPorId(Long id);

    Certificado insertarCertificado(Certificado certificado);

    Certificado actualizarCertificado(Certificado certificado);

    Boolean eliminarCertificado(Long id);
}
