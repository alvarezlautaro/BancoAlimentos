package com.group6.BancoAlimentos.Features.Donantes.model;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name="donantes")
public class Donante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donante")
    private Long id;
    @Column(name = "razon_social",nullable = false)
    private String razonSocial;
    @Column(name = "cuit_donante",nullable = false)
    private Long cuit;
    @Column(name = "telefono_donante",nullable = false)
    private String telefono;
    @Column(name = "email_donante",nullable = false)
    private String email;
    @Column(name = "direccion_donante",nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "donante")
    private List<Donacion> donaciones;

}
