package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Experiencia {
    Integer id;
    Integer idHojaDeVida;
    String tipo;
    String institucion;
    String cargoTitulo;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    String descripcion;
}
