package com.itm.edu.co.ProyectoPP_2026.Repositories;

import com.itm.edu.co.ProyectoPP_2026.Identities.Auditoria;
import com.itm.edu.co.ProyectoPP_2026.Identities.Notificacion;
import com.itm.edu.co.ProyectoPP_2026.Utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuditoriaRepository {

    @Autowired
    private AuditoriaRepositoryDAOHelper helper;

    public List<Auditoria> listarAuditoria(){
        List<Auditoria> auditoriaList = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarAuditorias());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Auditoria auditoria = Auditoria.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .accion(rs.getString("accion"))
                        .tablaAfectada(rs.getString("tabla_afectada"))
                        .fecha(rs.getObject("fecha",java.time.LocalDate.class))
                        .detalles(rs.getString("detalles"))
                        .build();
                auditoriaList.add(auditoria);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {}
        }
        return auditoriaList;
    }

    public Auditoria listarPorId(Integer id) {
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        Auditoria auditoriaId = Auditoria.builder().build();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.listarPorId(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                auditoriaId = Auditoria.builder()
                        .id(rs.getInt("id"))
                        .idUsuario(rs.getInt("id_usuario"))
                        .accion(rs.getString("accion"))
                        .tablaAfectada(rs.getString("tabla_afectada"))
                        .fecha(rs.getObject("fecha",java.time.LocalDate.class))
                        .detalles(rs.getString("detalles"))
                        .build();
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (Exception connectionException) {

            }
        }
        return auditoriaId;
    }

    public Auditoria insertarAuditoria(Auditoria auditoria) {
        Auditoria result = auditoria;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();
        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarAuditoria());
            ps.setInt(1, auditoria.getIdUsuario());
            ps.setString(2, auditoria.getAccion());
            ps.setString(3, auditoria.getTablaAfectada());
            ps.setDate(4, java.sql.Date.valueOf(auditoria.getFecha()));
            ps.setString(5, auditoria.getDetalles());
            ps.executeUpdate();

            ps = connection.prepareStatement(helper.listarPorUsuario());
            ps.setInt(1, auditoria.getIdUsuario());
            ResultSet rs = ps.executeQuery();

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

}