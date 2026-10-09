package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Interfaces.IAdministradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final IAdministradorService service;

    public AdministradorController(IAdministradorService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Administrador>> listarAdministrador() {
        List<Administrador> administradores = service.listaAdministrador();
        if (administradores == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Administrador> listarAdministradorId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador administrador = service.listarPorId(id);
        if (administrador == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (administrador.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(administrador);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Administrador> insertarAdministrador(@RequestBody Administrador administrador) {
        if (administrador == null || administrador.getIdUsuario() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador result = service.insertarAdministrador(administrador);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Administrador> actualizarAdministrador(@RequestBody Administrador administrador) {
        if (administrador == null || administrador.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Administrador result = service.actualizarAdministrador(administrador);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarAdministrador(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarAdministrador(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(result);
    }
}