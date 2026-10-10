package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.CertificadoServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController implements CertificadoApi {

    private final CertificadoServiceInterface service;

    public CertificadoController(CertificadoServiceInterface service) {
        this.service = service;
    }

    //@GetMapping("/listar")
    public ResponseEntity<List<Certificado>> listarCertificados() {
        List<Certificado> certificados = service.listarCertificados();
        if (certificados == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(certificados, HttpStatus.OK);
    }

    //@GetMapping("/listar/{id}")
    public ResponseEntity<Certificado> buscarPorId(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Certificado certificado = service.buscarPorId(id);
        if (certificado == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (certificado.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(certificado, HttpStatus.OK);
    }

    //@PostMapping("/insertar")
    public ResponseEntity<Certificado> crearCertificado(@RequestBody Certificado certificado) {
        if (certificado == null || certificado.getIdEstudiante() == null || certificado.getHorasTotales() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Certificado result = service.insertarCertificado(certificado);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //@PutMapping("/actualizar")
    public ResponseEntity<Certificado> actualizarCertificado(@RequestBody Certificado certificado) {
        if (certificado == null || certificado.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Certificado result = service.actualizarCertificado(certificado);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarCertificado(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarCertificado(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
