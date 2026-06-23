package com.group6.BancoAlimentos.Features.Donantes.service;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDonanteService {

    List<DonanteResponseDTO> findAll();
    DonanteResponseDTO save (DonanteRequestDTO donanteRequestDTO);
    Optional<DonanteResponseDTO> findById(Long id);
    DonanteResponseDTO update(Long id,DonanteRequestDTO dto);
    void delete(Long id);
    public List<Donante> getHistorial(Long id);
}
