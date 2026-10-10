package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.PostulacionOfertaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones-oferta")
public class PostulacionOfertaController implements PostulacionOfertaApi {

    private final PostulacionOfertaServiceInterface service;

    public PostulacionOfertaController(PostulacionOfertaServiceInterface service) {
        this.service = service;
    }

    //@GetMapping("/listar")
    public ResponseEntity<List<PostulacionOferta>> listarPostulaciones() {
        List<PostulacionOferta> postulaciones = service.listarPostulaciones();
        if (postulaciones == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postulaciones, HttpStatus.OK);
    }

    //@GetMapping("/listar/{id}")
    public ResponseEntity<PostulacionOferta> buscarPorId(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionOferta postulacion = service.buscarPorId(id);
        if (postulacion == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (postulacion.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postulacion, HttpStatus.OK);
    }

    //@PostMapping("/insertar")
    public ResponseEntity<PostulacionOferta> crearPostulacion(@RequestBody PostulacionOferta postulacion) {
        if (postulacion == null || postulacion.getIdEstudiante() == null || postulacion.getIdOferta() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionOferta result = service.insertarPostulacion(postulacion);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //@PutMapping("/actualizar")
    public ResponseEntity<PostulacionOferta> actualizarPostulacion(@RequestBody PostulacionOferta postulacion) {
        if (postulacion == null || postulacion.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionOferta result = service.actualizarPostulacion(postulacion);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarPostulacion(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarPostulacion(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
