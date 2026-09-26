package com.itm.edu.co.ProyectoPP_2026.identities;

import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OfertaLaborSocial {
    Integer id;
    Integer idEntidad;
    String titulo;
    String descripcion;
    Integer cuposDisponibles;
    Integer horasOfrecidas;
    String horario;
    String ubicacion;
    Date fechaInicio;
    Date fechaFin;
    String estado;
}