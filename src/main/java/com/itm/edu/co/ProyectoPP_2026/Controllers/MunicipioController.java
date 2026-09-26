package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Services.MunicipioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/municipios")
public class MunicipioController {

    private final MunicipioService service;

    public MunicipioController(MunicipioService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Municipio>> listarMunicipio(){
        ResponseEntity<List<Municipio>> responseEntity;
        List<Municipio> municipios = service.listaMunicipio();
        try{
            if (municipios == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(municipios, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Municipio> listarMunicipioId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Municipio municipio = service.listarPorId(id);
        if (municipio == null) {
            return new ResponseEntity(municipio, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (municipio.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(municipio, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Municipio> insertarMunicipio(@RequestBody Municipio municipio){
        if(municipio == null || municipio.getCodigoDane() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Municipio result = service.insertarMunicipio(municipio);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Municipio> actualizarMunicipio(@RequestBody Municipio municipio){
        if(municipio == null || municipio.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Municipio result = service.actualizarMunicipio(municipio);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarMunicipio(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarMunicipio(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}