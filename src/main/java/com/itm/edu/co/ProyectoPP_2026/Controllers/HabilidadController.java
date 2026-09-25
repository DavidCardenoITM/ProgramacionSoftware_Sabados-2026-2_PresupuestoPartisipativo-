package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Habilidad;
import com.itm.edu.co.ProyectoPP_2026.Services.HabilidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habilidades")
public class HabilidadController {

    private final HabilidadService service;

    public HabilidadController(HabilidadService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Habilidad>> listarHabilidad() {
        ResponseEntity<List<Habilidad>> responseEntity;
        List<Habilidad> habilidades = service.listaHabilidad();
        try {
            if (habilidades == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(habilidades, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Habilidad> listarHabilidadId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Habilidad habilidad = service.listarPorId(id);
        if (habilidad == null) {
            return new ResponseEntity(habilidad, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (habilidad.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(habilidad, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Habilidad> insertarHabilidad(@RequestBody Habilidad habilidad) {
        if (habilidad == null || habilidad.getNombre() == null || habilidad.getCategoria() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // uq_habilidad_nombre: el repository devuelve null si el nombre ya existe (Duplicate entry).
        Habilidad result = service.insertarHabilidad(habilidad);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
