package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RepresentanteEntidad {
    Integer id;
    Integer idUsuario;
    Integer idEntidad;
    String cargo;
}
