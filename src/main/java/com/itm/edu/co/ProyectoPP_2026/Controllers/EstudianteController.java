package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Estudiante;
import com.itm.edu.co.ProyectoPP_2026.Services.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Estudiante>> listarEstudiante(){
        ResponseEntity<List<Estudiante>> responseEntity;
        List<Estudiante> estudiantes = service.listaEstudiante();
        try{
            if (estudiantes == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(estudiantes, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Estudiante> listarEstudianteId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Estudiante estudiante = service.listarPorId(id);
        if (estudiante == null) {
            return new ResponseEntity(estudiante, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (estudiante.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(estudiante, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Estudiante> insertarEstudiante(@RequestBody Estudiante estudiante){
        if(estudiante == null || estudiante.getIdUsuario() == null || estudiante.getDocumentoIdentidad() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Estudiante result = service.insertarEstudiante(estudiante);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Estudiante> actualizarEstudiante(@RequestBody Estudiante estudiante){
        if(estudiante == null || estudiante.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Estudiante result = service.actualizarEstudiante(estudiante);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarEstudiante(@PathVariable Integer id){
        if(id == null || id < 0){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarEstudiante(id);
        if(result == false){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
