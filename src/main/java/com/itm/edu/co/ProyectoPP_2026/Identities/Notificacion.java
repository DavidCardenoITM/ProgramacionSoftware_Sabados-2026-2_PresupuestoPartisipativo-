package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Notificacion {
    Integer id;
    Integer idUsuario;
    String tipo;
    String mensaje;
    LocalDate fecha;
    Boolean leido;
}