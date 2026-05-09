package com.group6.BancoAlimentos.Features.Institucion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "institucion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Institucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private tipoInstitucion tipo;

    private String direccion;

    private String telefono;

    private String email;

    @Enumerated(EnumType.STRING)
    private estadoPago estado;
}
