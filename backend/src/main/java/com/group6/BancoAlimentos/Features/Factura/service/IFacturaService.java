package com.group6.BancoAlimentos.Features.Factura.service;

import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaRequestDTO;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IFacturaService {

    List<FacturaResponseDTO> findAll();

    Optional<FacturaResponseDTO> findById(Long id);

    FacturaResponseDTO save(FacturaRequestDTO dto);

    FacturaResponseDTO update(Long id, FacturaRequestDTO dto);

    void delete(Long id);

    FacturaResponseDTO generarFactura(Long idDonacion);
}