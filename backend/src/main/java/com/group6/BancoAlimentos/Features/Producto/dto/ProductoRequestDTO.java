package com.group6.BancoAlimentos.Features.Producto.dto;

import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;
import com.group6.BancoAlimentos.Features.Producto.emun.UnidadMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Size(max = 55,message = "El nombre del producto no puede superar los 55 caracteres")
    private String nombre;

    @NotNull(message = "La categoria del producto no puede ser nula")
    private Categoria categoria;

    @NotNull(message = "La unidad de medida del producto no puede ser nula")
    private UnidadMedida unidadMedida;




}
