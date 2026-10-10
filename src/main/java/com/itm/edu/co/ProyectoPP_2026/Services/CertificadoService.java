package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Repositories.CertificadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    public List<Certificado> obtenerTodos() {
        return certificadoRepository.findAll();
    }

    public boolean guardar(Certificado certificado) {
        return certificadoRepository.save(certificado);
    }
}