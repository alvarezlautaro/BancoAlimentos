package com.group6.BancoAlimentos.Features.Donantes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name="donantes")
public class Donante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razon_social;
    private Long cuit;
    private String telefono;
    private String email;
    private String direccion;


}
