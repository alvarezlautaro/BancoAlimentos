package com.group6.BancoAlimentos.service;

import com.group6.BancoAlimentos.dto.ProductoRequestDTO;
import com.group6.BancoAlimentos.dto.ProductoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IProductoService {

    List<ProductoResponseDTO> findAll();
    ProductoResponseDTO save (ProductoRequestDTO productoRequestDTO);
    Optional<ProductoResponseDTO> findById(Long id);
    ProductoResponseDTO update(Long id ,ProductoRequestDTO dto);
    void delete(Long id);

}
