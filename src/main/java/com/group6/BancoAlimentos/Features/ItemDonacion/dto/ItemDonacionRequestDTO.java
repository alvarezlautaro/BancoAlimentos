package com.group6.BancoAlimentos.Features.ItemDonacion.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ItemDonacionRequestDTO {

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser futura")
    private Date fechaVencimiento;

    @Positive(message = "El valor unitario debe ser mayor a 0")
    private double valorUnitario;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La donacion es obligatoria")
    private Long donacionId;
}
