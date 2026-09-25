package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Services.EntidadReceptoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entidades")
public class EntidadReceptoraController {

    private final EntidadReceptoraService service;

    public EntidadReceptoraController(EntidadReceptoraService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EntidadReceptora>> listarEntidad(){
        ResponseEntity<List<EntidadReceptora>> responseEntity;
        List<EntidadReceptora> entidades = service.listaEntidad();
        try{
            if (entidades == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(entidades, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<EntidadReceptora> listarEntidadId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EntidadReceptora entidad = service.listarPorId(id);
        if (entidad == null) {
            return new ResponseEntity(entidad, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (entidad.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(entidad, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<EntidadReceptora> insertarEntidad(@RequestBody EntidadReceptora entidad){
        if(entidad == null || entidad.getNit() == null || entidad.getNombre() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EntidadReceptora result = service.insertarEntidad(entidad);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<EntidadReceptora> actualizarEntidad(@RequestBody EntidadReceptora entidad){
        if(entidad == null || entidad.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EntidadReceptora result = service.actualizarEntidad(entidad);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarEntidad(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarEntidad(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
