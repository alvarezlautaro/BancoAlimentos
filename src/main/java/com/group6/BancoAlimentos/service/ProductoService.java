package com.group6.BancoAlimentos.service;

import com.group6.BancoAlimentos.dto.ProductoRequestDTO;
import com.group6.BancoAlimentos.dto.ProductoResponseDTO;
import com.group6.BancoAlimentos.mapper.ProductoMapper;
import com.group6.BancoAlimentos.model.Producto;
import com.group6.BancoAlimentos.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream().map(ProductoMapper::toResponse)
                .toList();
    }

    @Override
    public ProductoResponseDTO save(ProductoRequestDTO productoRequestDTO) {
        Producto producto = ProductoMapper.toEntity(productoRequestDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.toResponse(productoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoResponseDTO> findById(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toResponse);
    }

    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id).orElseThrow(()-> new RuntimeException("El producto no existe"));

        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setUnidadMedida(dto.getUnidadMedida());

        Producto productoActualizado =  productoRepository.save(producto);

        return ProductoMapper.toResponse(productoActualizado);
    }

    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("El producto no existe");
        }

        productoRepository.deleteById(id);
    }
}
