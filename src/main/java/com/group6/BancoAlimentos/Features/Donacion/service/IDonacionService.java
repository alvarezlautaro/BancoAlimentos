package com.group6.BancoAlimentos.Features.Donacion.service;

import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IDonacionService {

        List<DonacionResponseDTO> findAll();

        Optional<DonacionResponseDTO> findById(Long id);

        DonacionResponseDTO save(DonacionRequestDTO dto);

        DonacionResponseDTO update(Long id, DonacionRequestDTO dto);

        void delete(Long id);

        DonacionResponseDTO confirmarDonacion(Long id);
}
