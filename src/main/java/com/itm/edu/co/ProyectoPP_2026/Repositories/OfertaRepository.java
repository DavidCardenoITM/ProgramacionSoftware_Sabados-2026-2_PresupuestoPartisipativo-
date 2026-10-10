package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.OfertaRepositoryInterface;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfertaRepository implements OfertaRepositoryInterface {

    @Autowired
    private OfertaRepositoryDAOHelper helper;

    private OfertaLaborSocial mapear(ResultSet rs) throws SQLException {
        return OfertaLaborSocial.builder()
                .id(rs.getInt("id"))
                .idEntidad(rs.getInt("id_entidad"))
                .titulo(rs.getString("titulo"))
                .descripcion(rs.getString("descripcion"))
                .cuposDisponibles(rs.getInt("cupos_disponibles"))
                .horasOfrecidas(rs.getInt("horas_ofrecidas"))
                .horario(rs.getString("horario"))
                .ubicacion(rs.getString("ubicacion"))
                .estado(rs.getString("estado"))
                .build();
    }

    @Override
    public List<OfertaLaborSocial> listarOfertas() {
        List<OfertaLaborSocial> result = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarOfertas());
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
    public OfertaLaborSocial buscarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        OfertaLaborSocial result = OfertaLaborSocial.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.buscarPorId());
            ps.setInt(1, id);
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
    public OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        OfertaLaborSocial result = OfertaLaborSocial.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarOferta(), Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, oferta.getIdEntidad());
            ps.setString(2, oferta.getTitulo());
            ps.setString(3, oferta.getDescripcion());
            ps.setInt(4, oferta.getCuposDisponibles());
            ps.setInt(5, oferta.getHorasOfrecidas());
            ps.setString(6, oferta.getHorario());
            ps.setString(7, oferta.getUbicacion());
            ps.setString(8, oferta.getEstado());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                result = buscarPorId(keys.getInt(1));
            } else {
                result = oferta;
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
    public OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.actualizarOferta());
            ps.setString(1, oferta.getTitulo());
            ps.setString(2, oferta.getDescripcion());
            ps.setInt(3, oferta.getCuposDisponibles());
            ps.setInt(4, oferta.getHorasOfrecidas());
            ps.setString(5, oferta.getHorario());
            ps.setString(6, oferta.getUbicacion());
            ps.setString(7, oferta.getEstado());
            ps.setInt(8, oferta.getId());
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
        return oferta;
    }

    @Override
    public Boolean eliminarOferta(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarOferta());
            ps.setInt(1, id);
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
