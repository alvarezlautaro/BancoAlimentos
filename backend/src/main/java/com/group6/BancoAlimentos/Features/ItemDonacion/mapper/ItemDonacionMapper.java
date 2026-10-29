package com.group6.BancoAlimentos.Features.ItemDonacion.mapper;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.Producto.model.Producto;

public class ItemDonacionMapper {

    public static ItemDonacion aEntidad(ItemDonacionRequestDTO dto, Producto producto, Donacion donacion){

        ItemDonacion item = new ItemDonacion();
        item.setFechaVencimiento(dto.getFechaVencimiento());
        item.setValorUnitario(dto.getValorUnitario());
        item.setCantidad(dto.getCantidad());
        item.setProducto(producto);
        item.setDonacion(donacion);
        return item;

    }

    public static ItemDonacionResponseDTO aResponse(ItemDonacion item){

        ItemDonacionResponseDTO dto=new ItemDonacionResponseDTO();

        dto.setId(item.getId());
        dto.setFechaVencimiento(item.getFechaVencimiento());
        dto.setValorUnitario(item.getValorUnitario());
        dto.setCantidad(item.getCantidad());
        dto.setProductoNombre(item.getProducto().getNombre());
        dto.setDonante(item.getDonacion().getDonante().getRazonSocial());
        return dto;

    }
}
