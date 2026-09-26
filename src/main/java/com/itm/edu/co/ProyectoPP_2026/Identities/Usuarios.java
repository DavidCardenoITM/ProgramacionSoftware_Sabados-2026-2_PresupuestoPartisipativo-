package com.itm.edu.co.ProyectoPP_2026.Identities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Usuarios {
    Integer id;
    String nombres;
    String apellidos;
    String correo;
    String contrasenaHash;
    String rol;
    LocalDate fechaRegistro;
    String estado;
}
