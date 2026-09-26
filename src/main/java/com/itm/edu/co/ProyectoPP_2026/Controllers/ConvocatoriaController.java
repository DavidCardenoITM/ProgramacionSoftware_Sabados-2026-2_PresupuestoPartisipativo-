package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Convocatoria;
import com.itm.edu.co.ProyectoPP_2026.Services.ConvocatoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convocatorias")
public class ConvocatoriaController {

    private final ConvocatoriaService service;

    public ConvocatoriaController(ConvocatoriaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Convocatoria>> listarConvocatoria(){
        ResponseEntity<List<Convocatoria>> responseEntity;
        List<Convocatoria> convocatorias = service.listaConvocatoria();
        try{
            if (convocatorias == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(convocatorias, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Convocatoria> listarConvocatoriaId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Convocatoria convocatoria = service.listarPorId(id);
        if (convocatoria == null) {
            return new ResponseEntity(convocatoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (convocatoria.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(convocatoria, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Convocatoria> insertarConvocatoria(@RequestBody Convocatoria convocatoria){
        if(convocatoria == null || convocatoria.getIdMunicipio() == null || convocatoria.getNombre() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Convocatoria result = service.insertarConvocatoria(convocatoria);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Convocatoria> actualizarConvocatoria(@RequestBody Convocatoria convocatoria){
        if(convocatoria == null || convocatoria.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Convocatoria result = service.actualizarConvocatoria(convocatoria);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarConvocatoria(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarConvocatoria(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
