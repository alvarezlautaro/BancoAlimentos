package com.group6.BancoAlimentos.Features.Remito.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemitoDTO {
    private Long id;

    private LocalDate fecha;

    private Long idInstitucion;
}
