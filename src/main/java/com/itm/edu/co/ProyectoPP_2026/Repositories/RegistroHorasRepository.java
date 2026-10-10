package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.RegistroHoras;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.RegistroHorasRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroHorasRepository implements RegistroHorasRepositoryInterface {

    @Autowired
    private RegistroHorasRepositoryDAOHelper helper;

    private RegistroHoras mapear(ResultSet rs) throws SQLException {
        return new RegistroHoras(
                rs.getLong("id"),
                rs.getLong("id_postulacion_oferta"),
                rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null,
                rs.getString("hora_inicio"),
                rs.getString("hora_fin"),
                rs.getDouble("horas_trabajadas"),
                rs.getString("descripcion_actividad"),
                rs.getString("estado_validacion")
        );
    }

    @Override
    public List<RegistroHoras> listarRegistros() {
        List<RegistroHoras> result = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarRegistrosHoras());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapear(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return result;
    }

    @Override
    public RegistroHoras buscarPorId(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        RegistroHoras result = new RegistroHoras();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = mapear(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return result;
    }

    @Override
    public RegistroHoras insertarRegistro(RegistroHoras registro) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        RegistroHoras result = new RegistroHoras();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarRegistroHoras(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, registro.getIdPostulacionOferta());
            ps.setDate(2, registro.getFecha() != null ? Date.valueOf(registro.getFecha()) : null);
            ps.setString(3, registro.getHoraInicio());
            ps.setString(4, registro.getHoraFin());
            ps.setDouble(5, registro.getHorasTrabajadas());
            ps.setString(6, registro.getDescripcionActividad());
            ps.setString(7, registro.getEstadoValidacion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                result = buscarPorId(keys.getLong(1));
            } else {
                result = registro;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return result;
    }

    @Override
    public RegistroHoras actualizarRegistro(RegistroHoras registro) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.actualizarRegistroHoras());
            ps.setLong(1, registro.getIdPostulacionOferta());
            ps.setDate(2, registro.getFecha() != null ? Date.valueOf(registro.getFecha()) : null);
            ps.setString(3, registro.getHoraInicio());
            ps.setString(4, registro.getHoraFin());
            ps.setDouble(5, registro.getHorasTrabajadas());
            ps.setString(6, registro.getDescripcionActividad());
            ps.setString(7, registro.getEstadoValidacion());
            ps.setLong(8, registro.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return registro;
    }

    @Override
    public Boolean eliminarRegistro(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarRegistroHoras());
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (Exception connectionException) {
            }
        }
        return true;
    }
}
