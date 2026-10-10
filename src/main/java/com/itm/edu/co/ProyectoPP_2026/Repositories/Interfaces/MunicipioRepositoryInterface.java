package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Municipio;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MunicipioRepositoryInterface {

     List<Municipio> listarMunicipio();

     Municipio listarPorId(Integer id);

     Municipio insertarMunicipio(Municipio municipio);

     Municipio actualizarMunicipio(Municipio municipio);

     Boolean eliminarMunicipio(Integer id);
}
