package com.group6.BancoAlimentos.Features.DetalleRemito.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleRemitoResponse {
    private Long id;

    private Long idRemito;

    private Long idItemDonacion;

    private Integer cantidad;

    private String nombreProducto;
}
