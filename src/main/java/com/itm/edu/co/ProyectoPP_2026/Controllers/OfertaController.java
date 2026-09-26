package com.itm.edu.co.ProyectoPP_2026.controllers;

import com.itm.edu.co.ProyectoPP_2026.identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public List<OfertaLaborSocial> listarOfertas() {
        return ofertaService.listarOfertas();
    }

    @GetMapping("/{id}")
    public OfertaLaborSocial buscarPorId(@PathVariable Integer id) {
        return ofertaService.buscarPorId(id);
    }

    @PostMapping
    public Boolean insertarOferta(@RequestBody OfertaLaborSocial oferta) {
        return ofertaService.insertarOferta(oferta);
    }

    @PutMapping
    public OfertaLaborSocial actualizarOferta(@RequestBody OfertaLaborSocial oferta) {
        return ofertaService.actualizarOferta(oferta);
    }

    @DeleteMapping("/{id}")
    public Boolean eliminarOferta(@PathVariable Integer id) {
        return ofertaService.eliminarOferta(id);
    }
}