package com.group6.BancoAlimentos.Features.Donacion.dto;

import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonacionRequestDTO {

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;

    @NotNull(message = "El estado no puede estar vacío")
    private EstadoDonacion estado;

    @Positive(message = "El numero de remito debe ser mayor a cero")
    private int nroRemitoProveedor;

    private String observaciones;

    @NotNull
    @Size(min = 1, message = "La donación debe tener al menos un ítem")
    private List<@Valid ItemDonacionRequestDTO> items;

    @NotNull(message = "El donante es obligatorio")
    private Long idDonante;

}