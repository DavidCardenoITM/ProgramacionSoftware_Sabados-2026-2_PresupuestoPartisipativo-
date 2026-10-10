package com.itm.edu.co.ProyectoPP_2026.Controllers;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController implements OfertaApi{

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public List<OfertaLaborSocial> listarOfertas() {
        return ofertaService.listarOfertas();
    }

    //@GetMapping("/{id}")
    public OfertaLaborSocial buscarPorId(@PathVariable Integer id) {
        return ofertaService.buscarPorId(id);
    }

    //@PostMapping
    public Boolean insertarOferta(@RequestBody OfertaLaborSocial oferta) {
        return ofertaService.insertarOferta(oferta);
    }

    //@PutMapping
    public OfertaLaborSocial actualizarOferta(@RequestBody OfertaLaborSocial oferta) {
        return ofertaService.actualizarOferta(oferta);
    }

    //@DeleteMapping("/{id}")
    public Boolean eliminarOferta(@PathVariable Integer id) {
        return ofertaService.eliminarOferta(id);
    }
}