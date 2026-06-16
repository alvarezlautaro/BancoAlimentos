package com.group6.BancoAlimentos.Features.ItemDonacion.model;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.model.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ItemDonacion")
public class ItemDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad",nullable = false,length = 55)
    private int cantidad;
    @Column(name = "valor_unitario",nullable = false)
    private double valorUnitario;
    @Column(name = "fecha_vencimiento",nullable = false)
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "producto_id",nullable = false)
    private Producto producto;


    @ManyToOne
    @JoinColumn(name = "id_donacion",nullable=false)
    private Donacion donacion;

}
