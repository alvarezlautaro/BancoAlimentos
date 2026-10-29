package com.group6.BancoAlimentos.Features.DetalleRemito.DTOs;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleRemitoResponse {
    private UUID externalId;

    private Long idRemito;

    private Long idItemDonacion;

    private Integer cantidad;
}
