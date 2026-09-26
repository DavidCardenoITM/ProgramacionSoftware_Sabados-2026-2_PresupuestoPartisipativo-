package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HojaDeVida {
    Integer id;
    Integer idEstudiante;
    String resumen;
    String fotoUrl;
    LocalDate fechaActualizacion;
}
