package com.group6.BancoAlimentos.Features.Producto.dto;

import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;
import com.group6.BancoAlimentos.Features.Producto.emun.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Categoria categoria;
    private UnidadMedida unidadMedida;

}