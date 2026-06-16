package com.group6.BancoAlimentos.Features.Producto.model;


import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;
import com.group6.BancoAlimentos.Features.Producto.emun.UnidadMedida;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "nombre",nullable = false,length = 55)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria",nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida",nullable = false)
    private UnidadMedida unidadMedida;


}
