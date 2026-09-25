package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.EstudianteHabilidad;
import com.itm.edu.co.ProyectoPP_2026.Services.EstudianteHabilidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tabla intermedia N:M sin columna "id" propia (clave primaria compuesta:
 * id_estudiante + id_habilidad). NO existe un /listar/{id} como en
 * las demás entidades.
 */
@RestController
@RequestMapping("/estudiante-habilidad")
public class EstudianteHabilidadController {

    private final EstudianteHabilidadService service;

    public EstudianteHabilidadController(EstudianteHabilidadService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EstudianteHabilidad>> listarEstudianteHabilidad() {
        ResponseEntity<List<EstudianteHabilidad>> responseEntity;
        List<EstudianteHabilidad> lista = service.listaEstudianteHabilidad();
        try {
            if (lista == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(lista, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/listar/estudiante/{idEstudiante}")
    public ResponseEntity<List<EstudianteHabilidad>> listarPorEstudiante(@PathVariable Integer idEstudiante) {
        if (idEstudiante == null || idEstudiante < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<EstudianteHabilidad> lista = service.listarPorEstudiante(idEstudiante);
        if (lista == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(lista, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<EstudianteHabilidad> insertarEstudianteHabilidad(@RequestBody EstudianteHabilidad eh) {
        if (eh == null || eh.getIdEstudiante() == null || eh.getIdHabilidad() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EstudianteHabilidad result = service.insertarEstudianteHabilidad(eh);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{idEstudiante}/{idHabilidad}")
    public ResponseEntity<Void> eliminarEstudianteHabilidad(@PathVariable Integer idEstudiante,
                                                            @PathVariable Integer idHabilidad) {
        if (idEstudiante == null || idHabilidad == null || idEstudiante < 0 || idHabilidad < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean eliminado = service.eliminarEstudianteHabilidad(idEstudiante, idHabilidad);
        if (!eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}