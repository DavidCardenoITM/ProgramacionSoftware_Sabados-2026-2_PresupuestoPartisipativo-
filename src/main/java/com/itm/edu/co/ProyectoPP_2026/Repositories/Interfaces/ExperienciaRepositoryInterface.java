package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Experiencia;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ExperienciaRepositoryInterface {

     List<Experiencia> listarExperiencia();

     Experiencia listarPorId(Integer id);

    /** Historial completo de experiencias de una hoja de vida (relación 1:N). */
     List<Experiencia> listarPorHojaDeVida(Integer idHojaDeVida);

     Experiencia insertarExperiencia(Experiencia experiencia);
}
