package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Habilidad {
    Integer id;
    String nombre;
    String categoria;
}
