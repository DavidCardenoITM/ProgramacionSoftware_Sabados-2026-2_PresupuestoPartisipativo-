package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Certificado;
import com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces.CertificadoRepositoryInterface;
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
public class CertificadoRepository implements CertificadoRepositoryInterface {

    @Autowired
    private CertificadoRepositoryDAOHelper helper;

    private Certificado mapear(ResultSet rs) throws SQLException {
        return new Certificado(
                rs.getLong("id"),
                rs.getLong("id_estudiante"),
                rs.getDate("fecha_emision") != null ? rs.getDate("fecha_emision").toLocalDate() : null,
                rs.getInt("horas_totales"),
                rs.getString("codigo_verificacion")
        );
    }

    @Override
    public List<Certificado> listarCertificados() {
        List<Certificado> result = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarCertificados());
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
    public Certificado buscarPorId(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Certificado result = new Certificado();
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
    public Certificado buscarPorCodigo(String codigoVerificacion) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Certificado result = new Certificado();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.buscarPorCodigo());
            ps.setString(1, codigoVerificacion);
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
    public Certificado insertarCertificado(Certificado certificado) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Certificado result = new Certificado();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarCertificado(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, certificado.getIdEstudiante());
            ps.setDate(2, certificado.getFechaEmision() != null ? Date.valueOf(certificado.getFechaEmision()) : null);
            ps.setInt(3, certificado.getHorasTotales());
            ps.setString(4, certificado.getCodigoVerificacion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                result = buscarPorId(keys.getLong(1));
            } else {
                result = certificado;
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
    public Certificado actualizarCertificado(Certificado certificado) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.actualizarCertificado());
            ps.setLong(1, certificado.getIdEstudiante());
            ps.setDate(2, certificado.getFechaEmision() != null ? Date.valueOf(certificado.getFechaEmision()) : null);
            ps.setInt(3, certificado.getHorasTotales());
            ps.setString(4, certificado.getCodigoVerificacion());
            ps.setLong(5, certificado.getId());
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
        return certificado;
    }

    @Override
    public Boolean eliminarCertificado(Long id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarCertificado());
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
