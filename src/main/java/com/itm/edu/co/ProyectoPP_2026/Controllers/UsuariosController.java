package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Usuarios;
import com.itm.edu.co.ProyectoPP_2026.Services.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuarios>> listarUsuario(){
        ResponseEntity<List<Usuarios>> responseEntity;
        List<Usuarios> personas = service.listaUsuario();
        try{
            if (personas == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity  = new ResponseEntity(personas, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuarios> listarUsuarioId(@PathVariable Integer id){
        Usuarios user = service.listarPorId(id);
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            return new ResponseEntity(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Usuarios> insertarUsuario(@RequestBody Usuarios usuario){
        if(usuario == null || usuario.getCorreo() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.insertarUsuario(usuario);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody Usuarios usuario){
        if(usuario == null || usuario.getCorreo() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.actualizarUsuario(usuario);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/corregircorreo")
    public ResponseEntity<Usuarios> corregirCorreo(@RequestParam String correo, @RequestParam String nuevoCorreo){
        if(ObjectUtils.isEmpty(correo) || ObjectUtils.isEmpty(nuevoCorreo)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuarios result = service.corregirCorreo(correo, nuevoCorreo);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarUsuario(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarUsuario(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
