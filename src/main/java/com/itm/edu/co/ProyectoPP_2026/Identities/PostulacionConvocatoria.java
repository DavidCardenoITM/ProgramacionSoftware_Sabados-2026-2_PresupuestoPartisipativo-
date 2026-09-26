package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostulacionConvocatoria {
    Integer id;
    Integer idEstudiante;
    Integer idConvocatoria;
    LocalDate fechaPostulacion;
    String estado;
    String motivoRechazo;
}
