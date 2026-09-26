package com.itm.edu.co.ProyectoPP_2026.Repositories;

import org.springframework.stereotype.Component;

@Component
public class PostulacionConvocatoriaRepositoryDAOHelper {

    public String listarPostulaciones(){
        return "select id,id_estudiante,id_convocatoria,fecha_postulacion,estado,motivo_rechazo from postulacion_convocatoria";
    }

    public String listarPorId(Integer id){
        return "select id,id_estudiante,id_convocatoria,fecha_postulacion,estado,motivo_rechazo from postulacion_convocatoria where id =" + id;
    }

    public String insertarPostulacion(){
        return " insert into postulacion_convocatoria (id_estudiante, id_convocatoria, fecha_postulacion, estado, motivo_rechazo)" +
                " values (?,?,?,?,?) ";
    }

    public String buscarPorEstudianteYConvocatoria(){
        return " select id,id_estudiante,id_convocatoria,fecha_postulacion,estado,motivo_rechazo from postulacion_convocatoria" +
                " where id_estudiante = ? and id_convocatoria = ? ";
    }

    public String actualizarPostulacion(){
        return " update postulacion_convocatoria set id_estudiante = ?, id_convocatoria = ?, fecha_postulacion = ?, estado = ?, motivo_rechazo = ? where id = ? ";
    }

    public String eliminarPostulacion(){
        return " delete from postulacion_convocatoria where id = ? ";
    }

}
