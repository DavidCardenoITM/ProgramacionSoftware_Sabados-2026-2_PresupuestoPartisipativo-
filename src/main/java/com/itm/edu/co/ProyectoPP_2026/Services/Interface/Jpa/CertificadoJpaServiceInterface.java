package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.CertificadoResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.CertificadoDtos.GenerarCertificadoRequest;

public interface CertificadoJpaServiceInterface {

    CertificadoResumen generar(GenerarCertificadoRequest request, Integer idUsuario);

    CertificadoResumen consultar(String codigoVerificacion);

    CertificadoResumen obtenerPorEstudiante(Long idEstudiante, Integer idUsuario);
}
