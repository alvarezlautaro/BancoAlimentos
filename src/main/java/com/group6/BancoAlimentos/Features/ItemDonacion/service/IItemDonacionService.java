package com.group6.BancoAlimentos.Features.ItemDonacion.service;

import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;


import java.util.List;

public interface IItemDonacionService {

    List<ItemDonacionResponseDTO> findAll();
    List<ItemDonacionResponseDTO> findByProductoId(Long productoId);
    List<ItemDonacionResponseDTO> findByDonacionId(Long donacionId);
    ItemDonacionResponseDTO findById(Long id);
    ItemDonacionResponseDTO update(Long id, ItemDonacionRequestDTO dto);


}
