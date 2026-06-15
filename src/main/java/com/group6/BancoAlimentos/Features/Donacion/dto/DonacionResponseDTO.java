package com.group6.BancoAlimentos.Features.Donacion.dto;

import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonacionResponseDTO {

    private Long id;
    private LocalDate fecha;
    private EstadoDonacion estado;
    private int nroRemitoProveedor;
    private String observaciones;
}