package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import com.itm.edu.co.ProyectoPP_2026.Services.AuditoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
public class AuditoriaController {

    private final AuditoriaService service;

    public AuditoriaController(AuditoriaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Auditoria>> listarAuditoria(){
        ResponseEntity<List<Auditoria>> responseEntity;
        List<Auditoria> auditorias = service.listaAuditoria();
        try{
            if (auditorias == null) {
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            responseEntity = new ResponseEntity(auditorias, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return responseEntity;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Auditoria> listarAuditoriaId(@PathVariable Integer id){
        if (id == null || id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Auditoria auditoria = service.listarPorId(id);
        if (auditoria == null) {
            return new ResponseEntity(auditoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (auditoria.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(auditoria, HttpStatus.OK);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Auditoria> insertarAuditoria(@RequestBody Auditoria auditoria){
        if(auditoria == null || auditoria.getIdUsuario() == null || auditoria.getAccion() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Auditoria result = service.insertarAuditoria(auditoria);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}