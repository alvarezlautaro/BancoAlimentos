package com.group6.BancoAlimentos.Features.DetalleRemito.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NuevoDetalleRemito {

    @NotNull(message = "El id del remito es obligatorio")
    private Long idRemito;

    @NotNull(message = "El id del item donacion es obligatorio")
    private Long idItemDonacion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
