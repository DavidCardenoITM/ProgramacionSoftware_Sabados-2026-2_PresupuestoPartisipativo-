package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Services.RegistroHorasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros-horas")
public class RegistroHorasController {

    private final RegistroHorasService registroHorasService;

    public RegistroHorasController(RegistroHorasService registroHorasService) {
        this.registroHorasService = registroHorasService;
    }

    @GetMapping
    public List<RegistroHoras> listarRegistros() {
        return registroHorasService.obtenerTodos();
    }

    @PostMapping
    public String crearRegistro(@RequestBody RegistroHoras registro) {
        boolean guardado = registroHorasService.guardar(registro);
        return guardado ? "Registro de horas creado exitosamente" : "Error al guardar el registro de horas";
    }
}