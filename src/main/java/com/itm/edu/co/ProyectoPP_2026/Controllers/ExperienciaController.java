package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
import org.springframework.http.HttpStatus;
import com.itm.edu.co.ProyectoPP_2026.Services.ExperienciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiencias")
public class ExperienciaController {

    private final ExperienciaService service;

    public ExperienciaController(ExperienciaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Experiencia>> listarExperiencia() {
        ResponseEntity<List<Experiencia>> responseEntity;
        List<Experiencia> experiencias = service.listaExperiencia();
        try {
            if (experiencias == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(experiencias, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Experiencia> listarExperienciaId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Experiencia experiencia = service.listarPorId(id);
        if (experiencia == null) {
            return new ResponseEntity(experiencia, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (experiencia.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(experiencia, HttpStatus.OK);
    }

    /** Historial completo de experiencias de una hoja de vida (relación 1:N). */
    @GetMapping("/listar/hoja-de-vida/{idHojaDeVida}")
    public ResponseEntity<List<Experiencia>> listarExperienciaPorHojaDeVida(@PathVariable Integer idHojaDeVida) {
        if (idHojaDeVida == null || idHojaDeVida < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Experiencia> experiencias = service.listarPorHojaDeVida(idHojaDeVida);
        if (experiencias == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(experiencias, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Experiencia> insertarExperiencia(@RequestBody Experiencia experiencia) {
        if (experiencia == null || experiencia.getIdHojaDeVida() == null || experiencia.getTipo() == null
                || experiencia.getInstitucion() == null || experiencia.getCargoTitulo() == null
                || experiencia.getFechaInicio() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // La tabla exige fecha_fin IS NULL OR fecha_fin >= fecha_inicio (ck_experiencia_fechas);
        // se valida antes para devolver un 400 claro en vez de un 500 por el CHECK.
        if (experiencia.getFechaFin() != null && experiencia.getFechaFin().isBefore(experiencia.getFechaInicio())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Experiencia result = service.insertarExperiencia(experiencia);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}