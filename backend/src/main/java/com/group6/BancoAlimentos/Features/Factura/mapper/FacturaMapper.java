package com.group6.BancoAlimentos.Features.Factura.mapper;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaRequestDTO;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaResponseDTO;
import com.group6.BancoAlimentos.Features.Factura.model.Factura;

public class FacturaMapper {

    public static FacturaResponseDTO toResponse(Factura factura) {
        FacturaResponseDTO dto = new FacturaResponseDTO();

        dto.setId(factura.getId());
        dto.setIdDonacion(factura.getDonacion().getId());
        dto.setFecha(factura.getFecha());
        dto.setTipo(factura.getTipo());
        dto.setNombreDonante(factura.getDonacion().getDonante().getRazonSocial());

        return dto;
    }

    public static Factura toEntity(FacturaRequestDTO dto, Donacion donacion) {
        Factura factura = new Factura();

        factura.setDonacion(donacion);
        factura.setFecha(dto.getFecha());
        factura.setTipo(dto.getTipo());

        return factura;
    }
}