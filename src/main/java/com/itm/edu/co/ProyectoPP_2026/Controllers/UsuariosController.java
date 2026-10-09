package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Interfaces.IUsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final IUsuariosService service;

    public UsuariosController(IUsuariosService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuarios>> listarUsuario() {
        List<Usuarios> personas = service.listaUsuario();
        if (personas == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuarios> listarUsuarioId(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios user = service.listarPorId(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Usuarios> insertarUsuario(@RequestBody Usuarios usuario) {
        if (usuario == null || usuario.getCorreo() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.insertarUsuario(usuario);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody Usuarios usuario) {
        if (usuario == null || usuario.getCorreo() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.actualizarUsuario(usuario);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/corregircorreo")
    public ResponseEntity<Usuarios> corregirCorreo(@RequestParam String correo, @RequestParam String nuevoCorreo) {
        if (ObjectUtils.isEmpty(correo) || ObjectUtils.isEmpty(nuevoCorreo)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.corregirCorreo(correo, nuevoCorreo);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarUsuario(@PathVariable Integer id) {
        if (id == null || id < 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarUsuario(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(result);
    }
}