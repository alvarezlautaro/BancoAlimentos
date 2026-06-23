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
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String razonSocial;
    @Column(nullable = false)
    private String cuit;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "donante")
    private List<Donacion> donaciones;

}
