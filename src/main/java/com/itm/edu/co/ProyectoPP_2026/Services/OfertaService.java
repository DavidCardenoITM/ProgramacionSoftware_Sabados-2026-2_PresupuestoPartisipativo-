package com.itm.edu.co.ProyectoPP_2026.services;

import com.itm.edu.co.ProyectoPP_2026.identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public List<OfertaLaborSocial> listarOfertas() {
        return ofertaRepository.listarOfertas();
    }

    public OfertaLaborSocial buscarPorId(Integer id) {
        return ofertaRepository.buscarPorId(id);
    }

    public Boolean insertarOferta(OfertaLaborSocial oferta) {
        return ofertaRepository.insertarOferta(oferta);
    }

    public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
        return ofertaRepository.actualizarOferta(oferta);
    }

    public Boolean eliminarOferta(Integer id) {
        return ofertaRepository.eliminarOferta(id);
    }
}