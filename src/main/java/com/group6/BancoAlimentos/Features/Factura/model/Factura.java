package com.group6.BancoAlimentos.Features.Factura.model;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="id_factura")
    private Long id;


    @OneToOne
    @JoinColumn(name = "id_donacion",nullable = false,unique = true)
    private Donacion donacion;

    @Column(name = "fecha",nullable = false)
    private LocalDate fecha;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoFactura tipo;

}
