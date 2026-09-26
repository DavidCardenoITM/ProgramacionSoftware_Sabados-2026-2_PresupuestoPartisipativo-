package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Estudiante {
    Integer id;
    Integer idUsuario;
    String documentoIdentidad;
    Integer idMunicipio;
    String universidad;
}
