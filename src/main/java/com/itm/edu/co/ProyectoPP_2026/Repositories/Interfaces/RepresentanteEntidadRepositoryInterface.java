package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.RepresentanteEntidad;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RepresentanteEntidadRepositoryInterface {
     List<RepresentanteEntidad> listarRepresentante();

     RepresentanteEntidad listarPorId(Integer id);

     RepresentanteEntidad insertarRepresentante(RepresentanteEntidad representante);

     RepresentanteEntidad actualizarRepresentante(RepresentanteEntidad representante);

     Boolean eliminarRepresentante(Integer id);
}
