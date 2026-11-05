package com.itm.edu.co.ProyectoPP_2026.Identities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "text")
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "leido")
    private Boolean leido;
}
