package com.group6.BancoAlimentos.Features.Remito.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemitoDTO {
    private Long id;

    private LocalDate fecha;

    private Long idInstitucion;
    private String institucionNombre;
    private List<DetalleRemitoResponse> items;
}
