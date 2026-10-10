package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.OfertaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController implements OfertaApi {

    private final OfertaServiceInterface service;

    public OfertaController(OfertaServiceInterface service) {
        this.service = service;
    }

    //@GetMapping("/listar")
    public ResponseEntity<List<OfertaLaborSocial>> listarOfertas() {
        List<OfertaLaborSocial> ofertas = service.listarOfertas();
        if (ofertas == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }

    //@GetMapping("/listar/{id}")
    public ResponseEntity<OfertaLaborSocial> buscarPorId(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OfertaLaborSocial oferta = service.buscarPorId(id);
        if (oferta == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (oferta.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(oferta, HttpStatus.OK);
    }

    //@PostMapping("/insertar")
    public ResponseEntity<OfertaLaborSocial> insertarOferta(@RequestBody OfertaLaborSocial oferta) {
        if (oferta == null || oferta.getIdEntidad() == null || oferta.getTitulo() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OfertaLaborSocial result = service.insertarOferta(oferta);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //@PutMapping("/actualizar")
    public ResponseEntity<OfertaLaborSocial> actualizarOferta(@RequestBody OfertaLaborSocial oferta) {
        if (oferta == null || oferta.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OfertaLaborSocial result = service.actualizarOferta(oferta);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarOferta(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarOferta(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
