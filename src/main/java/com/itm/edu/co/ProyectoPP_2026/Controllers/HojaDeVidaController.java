package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Services.HojaDeVidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hojas-de-vida")
public class HojaDeVidaController {

    private final HojaDeVidaService service;

    public HojaDeVidaController(HojaDeVidaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<HojaDeVida>> listarHojaDeVida() {
        ResponseEntity<List<HojaDeVida>> responseEntity;
        List<HojaDeVida> hojas = service.listaHojaDeVida();
        try {
            if (hojas == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(hojas, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<HojaDeVida> listarHojaDeVidaId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HojaDeVida hoja = service.listarPorId(id);
        if (hoja == null) {
            return new ResponseEntity(hoja, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (hoja.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(hoja, HttpStatus.OK);
    }

    /** Relación 1:1 con Estudiante: cada estudiante consulta su propia hoja de vida. */
    @GetMapping("/listar/estudiante/{idEstudiante}")
    public ResponseEntity<HojaDeVida> listarHojaDeVidaPorEstudiante(@PathVariable Integer idEstudiante) {
        if (idEstudiante == null || idEstudiante < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HojaDeVida hoja = service.listarPorEstudiante(idEstudiante);
        if (hoja == null) {
            return new ResponseEntity(hoja, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (hoja.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(hoja, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<HojaDeVida> insertarHojaDeVida(@RequestBody HojaDeVida hoja) {
        if (hoja == null || hoja.getIdEstudiante() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HojaDeVida result = service.insertarHojaDeVida(hoja);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
