package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Services.AdministradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Administrador>> listarAdministrador(){
        ResponseEntity<List<Administrador>> responseEntity;
        List<Administrador> administradores = service.listaAdministrador();
        try{
            if (administradores == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(administradores, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Administrador> listarAdministradorId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador administrador = service.listarPorId(id);
        if (administrador == null) {
            return new ResponseEntity(administrador, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (administrador.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(administrador, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Administrador> insertarAdministrador(@RequestBody Administrador administrador){
        if(administrador == null || administrador.getIdUsuario() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador result = service.insertarAdministrador(administrador);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Administrador> actualizarAdministrador(@RequestBody Administrador administrador){
        if(administrador == null || administrador.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador result = service.actualizarAdministrador(administrador);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarAdministrador(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarAdministrador(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
