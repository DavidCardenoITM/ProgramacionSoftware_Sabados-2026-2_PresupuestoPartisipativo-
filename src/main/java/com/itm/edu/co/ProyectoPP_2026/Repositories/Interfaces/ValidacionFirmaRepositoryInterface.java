package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.ValidacionFirma;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ValidacionFirmaRepositoryInterface {
     List<ValidacionFirma> listarValidacionFirma();

     ValidacionFirma listarPorId(Integer id);

     ValidacionFirma insertarValidacion(ValidacionFirma validacion);
}
