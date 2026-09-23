package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Services.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Notificacion>> listarNotificacion(){
        ResponseEntity<List<Notificacion>> responseEntity;
        List<Notificacion> notificaciones = service.listaNotificacion();
        try{
            if (notificaciones == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(notificaciones, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Notificacion> listarNotificacionId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Notificacion notificacion = service.listarPorId(id);
        if (notificacion == null) {
            return new ResponseEntity(notificacion, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (notificacion.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(notificacion, HttpStatus.OK);
    }


    @PostMapping("/insertar")
    public ResponseEntity<Notificacion> insertarNotificacion(@RequestBody Notificacion notificacion){
        if(notificacion == null || notificacion.getIdUsuario() == null || notificacion.getMensaje() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Notificacion result = service.insertarNotificacion(notificacion);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Notificacion> actualizarNotificacion(@RequestBody Notificacion notificacion){
        if(notificacion == null || notificacion.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Notificacion result = service.actualizarNotificacion(notificacion);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/marcarleido/{id}")
    public ResponseEntity<Boolean> marcarLeido(@PathVariable Integer id, @RequestParam Boolean leido){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.marcarLeido(id, leido);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarNotificacion(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarNotificacion(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}