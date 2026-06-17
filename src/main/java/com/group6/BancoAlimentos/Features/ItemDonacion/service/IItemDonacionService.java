package com.group6.BancoAlimentos.Features.ItemDonacion.service;

import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;


import java.util.List;

public interface IItemDonacionService {

    List<ItemDonacionResponseDTO> findAll();
    List<ItemDonacionResponseDTO> findByProductoID(Long productoId);
    List<ItemDonacionResponseDTO> findByDonacionID(Long donacionId);
    ItemDonacionResponseDTO findById(Long id);
    ItemDonacionResponseDTO save(ItemDonacionRequestDTO dto);
    ItemDonacionResponseDTO update(Long id, ItemDonacionRequestDTO dto);
    List<ItemDonacionResponseDTO> findByCategoria(String categoria);
    void delete(Long id);


}
