package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionConvocatoria;
import com.itm.edu.co.ProyectoPP_2026.Services.PostulacionConvocatoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones-convocatoria")
public class PostulacionConvocatoriaController {

    private final PostulacionConvocatoriaService service;

    public PostulacionConvocatoriaController(PostulacionConvocatoriaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PostulacionConvocatoria>> listarPostulacion(){
        ResponseEntity<List<PostulacionConvocatoria>> responseEntity;
        List<PostulacionConvocatoria> postulaciones = service.listaPostulacion();
        try{
            if (postulaciones == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(postulaciones, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<PostulacionConvocatoria> listarPostulacionId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionConvocatoria postulacion = service.listarPorId(id);
        if (postulacion == null) {
            return new ResponseEntity(postulacion, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (postulacion.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(postulacion, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<PostulacionConvocatoria> insertarPostulacion(@RequestBody PostulacionConvocatoria postulacion){
        if(postulacion == null || postulacion.getIdEstudiante() == null || postulacion.getIdConvocatoria() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionConvocatoria result = service.insertarPostulacion(postulacion);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PostulacionConvocatoria> actualizarPostulacion(@RequestBody PostulacionConvocatoria postulacion){
        if(postulacion == null || postulacion.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostulacionConvocatoria result = service.actualizarPostulacion(postulacion);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarPostulacion(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarPostulacion(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
