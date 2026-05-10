package com.group6.BancoAlimentos.Features.Remito.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoRemitoDTO {
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha del remito no puede ser futura")
    LocalDate fecha;

    @NotNull(message = "Debe especificar la ID de la institución de destino")
    Long idInstitucion;

}
