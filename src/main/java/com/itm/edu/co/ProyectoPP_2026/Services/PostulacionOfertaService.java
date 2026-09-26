package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.PostulacionOferta;
import com.itm.edu.co.ProyectoPP_2026.Repositories.PostulacionOfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulacionOfertaService {

    private final PostulacionOfertaRepository postulacionOfertaRepository;

    public PostulacionOfertaService(PostulacionOfertaRepository postulacionOfertaRepository) {
        this.postulacionOfertaRepository = postulacionOfertaRepository;
    }

    public List<PostulacionOferta> obtenerTodas() {
        return postulacionOfertaRepository.findAll();
    }

    public boolean guardar(PostulacionOferta postulacion) {
        return postulacionOfertaRepository.save(postulacion);
    }
}