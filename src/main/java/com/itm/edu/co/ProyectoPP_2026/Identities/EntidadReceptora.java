package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EntidadReceptora {
    Integer id;
    String nombre;
    String nit;
    String sector;
    String direccion;
    Integer idMunicipio;
    String estadoValidacion;
}
