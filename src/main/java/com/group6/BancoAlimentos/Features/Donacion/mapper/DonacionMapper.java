package com.group6.BancoAlimentos.Features.Donacion.mapper;

import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;
import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;

public class DonacionMapper {

    public static DonacionResponseDTO toResponse(Donacion donacion) {
        DonacionResponseDTO dto = new DonacionResponseDTO();

        dto.setId(donacion.getId());
        dto.setFecha(donacion.getFecha());
        dto.setEstado(donacion.getEstado());
        dto.setNroRemitoProveedor(donacion.getNroRemitoProveedor());
        dto.setObservaciones(donacion.getObservaciones());

        return dto;
    }

    public static Donacion toEntity(DonacionRequestDTO dto) {
        Donacion donacion = new Donacion();

        donacion.setFecha(dto.getFecha());
        donacion.setEstado(dto.getEstado());
        donacion.setNroRemitoProveedor(dto.getNroRemitoProveedor());
        dto.setObservaciones(dto.getObservaciones());
        return donacion;
    }
}