package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Convocatoria {
    Integer id;
    Integer idMunicipio;
    String nombre;
    LocalDate fechaApertura;
    LocalDate fechaCierre;
    Integer cupos;
    String estado;
}
