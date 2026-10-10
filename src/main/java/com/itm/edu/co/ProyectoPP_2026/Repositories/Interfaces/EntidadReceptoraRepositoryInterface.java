package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EntidadReceptoraRepositoryInterface {
    List<EntidadReceptora> listarEntidad();

    EntidadReceptora listarPorId(Integer id);

    EntidadReceptora insertarEntidad(EntidadReceptora entidad);

    EntidadReceptora actualizarEntidad(EntidadReceptora entidad);

    Boolean eliminarEntidad(Integer id);
}
