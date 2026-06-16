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
    private Long id;
    private String razon_social;
    private Long cuit;
    private String telefono;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "donante")
    private List<Donacion> donaciones;

}
