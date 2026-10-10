package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.HojaDeVida;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface HojaDeVidaRepositoryInterface {

     List<HojaDeVida> listarHojaDeVida();

     HojaDeVida listarPorId(Integer id);

    /** Relación 1:1 con Estudiante (uq_hoja_estudiante): a lo sumo una fila por estudiante. */
     HojaDeVida listarPorEstudiante(Integer idEstudiante);

     HojaDeVida insertarHojaDeVida(HojaDeVida hoja);
}
