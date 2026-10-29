package com.group6.BancoAlimentos.Features.Remito.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemitoDTO {
    private UUID externalId;

    private LocalDate fecha;

    private Long idInstitucion;
}
