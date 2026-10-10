package com.itm.edu.co.ProyectoPP_2026.Services;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Repositories.RegistroHorasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroHorasService {

    private final RegistroHorasRepository registroHorasRepository;

    public RegistroHorasService(RegistroHorasRepository registroHorasRepository) {
        this.registroHorasRepository = registroHorasRepository;
    }

    public List<RegistroHoras> obtenerTodos() {
        return registroHorasRepository.findAll();
    }

    public boolean guardar(RegistroHoras registro) {
        return registroHorasRepository.save(registro);
    }
}