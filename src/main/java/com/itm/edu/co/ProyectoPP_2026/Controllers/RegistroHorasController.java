package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Services.Interface.RegistroHorasServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros-horas")
public class    RegistroHorasController implements RegistroHorasApi {

    private final RegistroHorasServiceInterface service;

    public RegistroHorasController(RegistroHorasServiceInterface service) {
        this.service = service;
    }

    //@GetMapping("/listar")
    public ResponseEntity<List<RegistroHoras>> listarRegistros() {
        List<RegistroHoras> registros = service.listarRegistros();
        if (registros == null) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    //@GetMapping("/listar/{id}")
    public ResponseEntity<RegistroHoras> buscarPorId(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RegistroHoras registro = service.buscarPorId(id);
        if (registro == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (registro.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    //@PostMapping("/insertar")
    public ResponseEntity<RegistroHoras> crearRegistro(@RequestBody RegistroHoras registro) {
        if (registro == null || registro.getIdPostulacionOferta() == null
                || registro.getFecha() == null || registro.getHoraInicio() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RegistroHoras result = service.insertarRegistro(registro);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //@PutMapping("/actualizar")
    public ResponseEntity<RegistroHoras> actualizarRegistro(@RequestBody RegistroHoras registro) {
        if (registro == null || registro.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RegistroHoras result = service.actualizarRegistro(registro);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarRegistro(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        Boolean result = service.eliminarRegistro(id);
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
