package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import com.itm.edu.co.ProyectoPP_2026.Services.RepresentanteEntidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/representantes")
public class RepresentanteEntidadController {

    private final RepresentanteEntidadService service;

    public RepresentanteEntidadController(RepresentanteEntidadService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RepresentanteEntidad>> listarRepresentante(){
        ResponseEntity<List<RepresentanteEntidad>> responseEntity;
        List<RepresentanteEntidad> representantes = service.listaRepresentante();
        try{
            if (representantes == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(representantes, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<RepresentanteEntidad> listarRepresentanteId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RepresentanteEntidad representante = service.listarPorId(id);
        if (representante == null) {
            return new ResponseEntity(representante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (representante.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(representante, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<RepresentanteEntidad> insertarRepresentante(@RequestBody RepresentanteEntidad representante){
        if(representante == null || representante.getIdUsuario() == null || representante.getIdEntidad() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RepresentanteEntidad result = service.insertarRepresentante(representante);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<RepresentanteEntidad> actualizarRepresentante(@RequestBody RepresentanteEntidad representante){
        if(representante == null || representante.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RepresentanteEntidad result = service.actualizarRepresentante(representante);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarRepresentante(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarRepresentante(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
