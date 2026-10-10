package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.Administrador;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AdministradorRepositoryInterface {

     List<Administrador> listarAdministrador();

     Administrador listarPorId(Integer id);

     Administrador insertarAdministrador(Administrador administrador);

     Administrador actualizarAdministrador(Administrador administrador);

     Boolean eliminarAdministrador(Integer id);
}
