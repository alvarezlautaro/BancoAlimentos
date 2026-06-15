package com.group6.BancoAlimentos.Features.Factura.DTO;

import com.group6.BancoAlimentos.Features.Factura.model.TipoFactura;
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
public class FacturaRequestDTO {

    @NotNull(message = "El id de la donación no puede estar vacío")
    private Long idDonacion;

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @NotNull(message = "El tipo de factura no puede estar vacío")
    private TipoFactura tipo;
}