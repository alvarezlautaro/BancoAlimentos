package com.group6.BancoAlimentos.Features.Donacion.service;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;
import com.group6.BancoAlimentos.Features.Donacion.mapper.DonacionMapper;
import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import com.group6.BancoAlimentos.Features.Donacion.repository.IDonacionRepository;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.Donantes.repository.IDonanteRepository;
import com.group6.BancoAlimentos.Features.Factura.model.Factura;
import com.group6.BancoAlimentos.Features.Factura.model.TipoFactura;
import com.group6.BancoAlimentos.Features.Factura.repository.IFacturaRepository;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.Producto.model.Producto;
import com.group6.BancoAlimentos.Features.Producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonacionService implements IDonacionService {

    private final IDonacionRepository donacionRepository;
    private final IDonanteRepository donanteRepository;
    private final ProductoRepository productoRepository;
    private final IFacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DonacionResponseDTO> findAll() {
        return donacionRepository.findAll()
                .stream()
                .map(DonacionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonacionResponseDTO> findById(Long id) {
        return donacionRepository.findById(id)
                .map(DonacionMapper::toResponse);
    }

    @Override
    public DonacionResponseDTO save(DonacionRequestDTO dto) {

        Donante donante=donanteRepository.findById(dto.getIdDonante()).
                orElseThrow(() -> new RecursoNoEncontradoException("Donante no encontrado"));

        Donacion donacion=new Donacion();
        donacion.setEstado(dto.getEstado());
        donacion.setObservaciones(dto.getObservaciones());
        donacion.setFecha(dto.getFecha());
        donacion.setNroRemitoProveedor(dto.getNroRemitoProveedor());
        donacion.setDonante(donante);

        List<ItemDonacion> items = dto.getItems().stream().map(itemDTO -> {
            Producto producto = productoRepository.findById(itemDTO.getProductoId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ItemDonacion item = new ItemDonacion();
            item.setFechaVencimiento(itemDTO.getFechaVencimiento());
            item.setValorUnitario(itemDTO.getValorUnitario());
            item.setCantidad(itemDTO.getCantidad());
            item.setProducto(producto);
            item.setDonacion(donacion);
            return item;
        }).collect(Collectors.toList());

        donacion.setItemDonaciones(items);
        Donacion donacionGuardada = donacionRepository.save(donacion);
        Factura factura = new Factura();
        factura.setDonacion(donacionGuardada);
        factura.setFecha(LocalDate.now());
        factura.setTipo(TipoFactura.C);
        facturaRepository.save(factura);

        return DonacionMapper.toResponse(donacionGuardada);
    }



    @Override
    public DonacionResponseDTO update(Long id, DonacionRequestDTO dto) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La donación no existe"));

        donacion.setFecha(dto.getFecha());
        donacion.setEstado(dto.getEstado());
        donacion.setNroRemitoProveedor(dto.getNroRemitoProveedor());
        donacion.setObservaciones(dto.getObservaciones());

        Donacion donacionActualizada = donacionRepository.save(donacion);

        return DonacionMapper.toResponse(donacionActualizada);
    }

    @Override
    public void delete(Long id) {
        if (!donacionRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("La donación no existe");
        }

        donacionRepository.deleteById(id);
    }

    @Override
    public DonacionResponseDTO confirmarDonacion(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La donación no existe"));

        donacion.setEstado(EstadoDonacion.CONFIRMADA);

        Donacion donacionConfirmada = donacionRepository.save(donacion);

        return DonacionMapper.toResponse(donacionConfirmada);
    }
}