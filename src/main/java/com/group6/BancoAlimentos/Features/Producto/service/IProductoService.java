package com.group6.BancoAlimentos.Features.Producto.service;

import com.group6.BancoAlimentos.Features.Producto.dto.ProductoRequestDTO;
import com.group6.BancoAlimentos.Features.Producto.dto.ProductoResponseDTO;
import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;

import java.util.List;
import java.util.Optional;


public interface IProductoService {

    List<ProductoResponseDTO> findAll();
    ProductoResponseDTO save (ProductoRequestDTO productoRequestDTO);
    Optional<ProductoResponseDTO> findById(Long id);
    ProductoResponseDTO update(Long id ,ProductoRequestDTO dto);
    void delete(Long id);
    List<ProductoResponseDTO> findByCategoria(Categoria categoria);

}
