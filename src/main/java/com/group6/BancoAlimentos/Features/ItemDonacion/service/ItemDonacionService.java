package com.group6.BancoAlimentos.Features.ItemDonacion.service;

import com.group6.BancoAlimentos.Features.DetalleRemito.IDetalleRemitoRepositorio;
import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Donacion.repository.IDonacionRepository;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.mapper.ItemDonacionMapper;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.repository.ItemDonacionRepository;
import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;
import com.group6.BancoAlimentos.Features.Producto.model.Producto;
import com.group6.BancoAlimentos.Features.Producto.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemDonacionService implements IItemDonacionService {

    private final ItemDonacionRepository itemDonacionRepository;
    private final ProductoRepository productoRepository;
    private final IDonacionRepository donacionRepository;
    private final IDetalleRemitoRepositorio detalleRemitoRepositorio;


    @Override
    public List<ItemDonacionResponseDTO> findAll() {
        return itemDonacionRepository.findAll().stream().map(ItemDonacionMapper::aResponse).toList();
    }

    @Override
    public List<ItemDonacionResponseDTO> findByProductoID(Long productoId) {
        return itemDonacionRepository.findByProducto_Id(productoId).stream().map(ItemDonacionMapper::aResponse)
                .toList();
    }

    @Override
    public List<ItemDonacionResponseDTO> findByDonacionID(Long donacionId) {
        return itemDonacionRepository.findByDonacion_Id(donacionId).stream().map(ItemDonacionMapper::aResponse)
                .toList();
    }



    @Override
    public ItemDonacionResponseDTO findById(Long id) {
        ItemDonacion item = itemDonacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemDonacion no encontrado con id: " + id));
        return ItemDonacionMapper.aResponse(item);
    }

    @Override
    public ItemDonacionResponseDTO save(ItemDonacionRequestDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + dto.getProductoId()));
        Donacion donacion = donacionRepository.findById(dto.getDonacionId())
                .orElseThrow(() -> new EntityNotFoundException("Donacion no encontrada con id: " + dto.getDonacionId()));
        ItemDonacion item = ItemDonacionMapper.aEntidad(dto, producto, donacion);
        itemDonacionRepository.save(item);
        return ItemDonacionMapper.aResponse(item);
    }

    @Override
    public ItemDonacionResponseDTO update(Long id, ItemDonacionRequestDTO dto) {
        itemDonacionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ItemDonacion no encontrado con id: " + id));

        Producto producto = productoRepository.findById(dto.getProductoId()).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + dto.getProductoId()));

        Donacion donacion = donacionRepository.findById(dto.getDonacionId()).orElseThrow(() -> new EntityNotFoundException("Donacion no encontrada con id: " + dto.getDonacionId()));

        ItemDonacion item = ItemDonacionMapper.aEntidad(dto, producto, donacion);
        item.setId(id);
        itemDonacionRepository.save(item);
        return ItemDonacionMapper.aResponse(item);
    }

    @Override
    public List<ItemDonacionResponseDTO> findByCategoria(String categoria) {
        Categoria categoriaEnum = Categoria.valueOf(categoria.toUpperCase());

        return itemDonacionRepository.findByProducto_Categoria(categoriaEnum)
                .stream()
                .map(ItemDonacionMapper::aResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        itemDonacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemDonacion no encontrado con id: " + id));

        if (detalleRemitoRepositorio.existsByItemDonacion_Id(id)) {
            throw new IllegalStateException("No se puede eliminar el ItemDonacion porque YA esta asociado a un Remito");
        }

        itemDonacionRepository.deleteById(id);
    }
}
