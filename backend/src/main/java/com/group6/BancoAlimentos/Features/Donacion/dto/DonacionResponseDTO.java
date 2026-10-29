package com.group6.BancoAlimentos.Features.Donacion.dto;

import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


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
    private List<ItemDonacionResponseDTO> items;
}