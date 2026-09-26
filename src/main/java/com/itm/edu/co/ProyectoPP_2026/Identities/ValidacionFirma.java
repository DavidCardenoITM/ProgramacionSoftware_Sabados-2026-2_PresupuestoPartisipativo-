package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ValidacionFirma {
    Integer id;
    Integer idRegistroHoras;
    Integer idRepresentante;
    LocalDateTime fechaValidacion;
    String resultado;
    String observaciones;
}