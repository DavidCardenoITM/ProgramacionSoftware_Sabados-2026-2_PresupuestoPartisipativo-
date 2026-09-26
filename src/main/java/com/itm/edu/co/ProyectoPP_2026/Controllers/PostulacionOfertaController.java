package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Services.PostulacionOfertaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones-oferta")
public class PostulacionOfertaController {

    private final PostulacionOfertaService postulacionOfertaService;

    public PostulacionOfertaController(PostulacionOfertaService postulacionOfertaService) {
        this.postulacionOfertaService = postulacionOfertaService;
    }

    @GetMapping
    public List<PostulacionOferta> listarPostulaciones() {
        return postulacionOfertaService.obtenerTodas();
    }

    @PostMapping
    public String crearPostulacion(@RequestBody PostulacionOferta postulacion) {
        boolean guardado = postulacionOfertaService.guardar(postulacion);
        return guardado ? "Postulación creada exitosamente" : "Error al guardar la postulación";
    }
}