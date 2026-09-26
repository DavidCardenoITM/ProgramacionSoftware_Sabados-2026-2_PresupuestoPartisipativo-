package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Services.CertificadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @GetMapping
    public List<Certificado> listarCertificados() {
        return certificadoService.obtenerTodos();
    }

    @PostMapping
    public String crearCertificado(@RequestBody Certificado certificado) {
        boolean guardado = certificadoService.guardar(certificado);
        return guardado ? "Certificado creado exitosamente" : "Error al guardar el certificado";
    }
}