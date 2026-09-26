package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Auditoria {
    Integer id;
    Integer idUsuario;
    String accion;
    String tablaAfectada;
    LocalDate fecha;
    String detalles;
}