package com.group6.BancoAlimentos.Features.Remito.DTOs;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarRemitoDTO {
    @PastOrPresent(message = "La fecha del remito no puede ser futura")
    LocalDate fecha;

    Long idInstitucion;
}
