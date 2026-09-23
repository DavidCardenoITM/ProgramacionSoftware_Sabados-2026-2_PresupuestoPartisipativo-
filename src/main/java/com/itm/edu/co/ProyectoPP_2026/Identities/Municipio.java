package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Municipio {
    Integer id;
    String nombre;
    String codigoDane;
}
