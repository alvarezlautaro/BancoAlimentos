package com.group6.BancoAlimentos.Features.Donacion.dto;

import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonacionRequestDTO {

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @NotNull(message = "El estado no puede estar vacío")
    private EstadoDonacion estado;

    @NotNull(message = "El numero de remito no puede estar vacío")
    private int nroRemitoProveedor;

    private String observaciones;

}