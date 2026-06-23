package com.group6.BancoAlimentos.Features.Producto.mapper;

//.<>
import com.group6.BancoAlimentos.Features.Producto.dto.ProductoRequestDTO;
import com.group6.BancoAlimentos.Features.Producto.dto.ProductoResponseDTO;
import com.group6.BancoAlimentos.Features.Producto.model.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO toResponse(Producto producto){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCategoria(producto.getCategoria());
        dto.setUnidadMedida(producto.getUnidadMedida());
        return dto;
    }


    public static Producto toEntity(ProductoRequestDTO productoRequestDTO){
        Producto producto=new Producto();
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setCategoria(productoRequestDTO.getCategoria());
        producto.setUnidadMedida(productoRequestDTO.getUnidadMedida());
        return producto;
    }

}
