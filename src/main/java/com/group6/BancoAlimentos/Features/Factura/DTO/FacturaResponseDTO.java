package com.group6.BancoAlimentos.Features.Factura.DTO;

import com.group6.BancoAlimentos.Features.Factura.model.TipoFactura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacturaResponseDTO {

    private Long id;
    private Long idDonacion;
    private LocalDate fecha;
    private TipoFactura tipo;
}