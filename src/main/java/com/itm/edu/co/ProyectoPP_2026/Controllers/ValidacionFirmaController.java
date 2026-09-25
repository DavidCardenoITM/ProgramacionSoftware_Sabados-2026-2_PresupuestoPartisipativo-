package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Services.ValidacionFirmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/validaciones-firma")
public class ValidacionFirmaController {

    private final ValidacionFirmaService service;

    public ValidacionFirmaController(ValidacionFirmaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ValidacionFirma>> listarValidacionFirma() {
        ResponseEntity<List<ValidacionFirma>> responseEntity;
        List<ValidacionFirma> validaciones = service.listaValidacionFirma();
        try {
            if (validaciones == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(validaciones, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ValidacionFirma> listarValidacionFirmaId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ValidacionFirma validacion = service.listarPorId(id);
        if (validacion == null) {
            return new ResponseEntity(validacion, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (validacion.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(validacion, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<ValidacionFirma> insertarValidacionFirma(@RequestBody ValidacionFirma validacion) {
        if (validacion == null || validacion.getIdRegistroHoras() == null
                || validacion.getIdRepresentante() == null || validacion.getResultado() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // La tabla exige resultado IN ('APROBADO','RECHAZADO'); se valida antes de llegar a la BD
        // para no depender únicamente del CHECK y devolver un 400 claro en vez de un 500.
        if (!validacion.getResultado().equals("APROBADO") && !validacion.getResultado().equals("RECHAZADO")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ValidacionFirma result = service.insertarValidacion(validacion);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}