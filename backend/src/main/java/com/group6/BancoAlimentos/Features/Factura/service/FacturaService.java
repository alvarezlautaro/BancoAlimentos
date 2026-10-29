package com.group6.BancoAlimentos.Features.Factura.service;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Donacion.repository.IDonacionRepository;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaRequestDTO;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaResponseDTO;
import com.group6.BancoAlimentos.Features.Factura.mapper.FacturaMapper;
import com.group6.BancoAlimentos.Features.Factura.model.Factura;
import com.group6.BancoAlimentos.Features.Factura.model.TipoFactura;
import com.group6.BancoAlimentos.Features.Factura.repository.IFacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FacturaService implements IFacturaService {

    private final IFacturaRepository facturaRepository;
    private final IDonacionRepository donacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FacturaResponseDTO> findAll() {
        return facturaRepository.findAll()
                .stream()
                .map(FacturaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaResponseDTO> findById(Long id) {
        return facturaRepository.findById(id)
                .map(FacturaMapper::toResponse);
    }

    @Override
    public FacturaResponseDTO save(FacturaRequestDTO dto) {
        Donacion donacion = donacionRepository.findById(dto.getIdDonacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("La donación no existe"));

        Factura factura = FacturaMapper.toEntity(dto, donacion);

        Factura facturaGuardada = facturaRepository.save(factura);

        return FacturaMapper.toResponse(facturaGuardada);
    }

    @Override
    public FacturaResponseDTO update(Long id, FacturaRequestDTO dto) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La factura no existe"));

        Donacion donacion = donacionRepository.findById(dto.getIdDonacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("La donación no existe"));

        factura.setDonacion(donacion);
        factura.setFecha(dto.getFecha());
        factura.setTipo(dto.getTipo());

        Factura facturaActualizada = facturaRepository.save(factura);

        return FacturaMapper.toResponse(facturaActualizada);
    }

    @Override
    public void delete(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("La factura no existe");
        }

        facturaRepository.deleteById(id);
    }

    @Override
    public FacturaResponseDTO generarFactura(Long idDonacion) {
        Donacion donacion = donacionRepository.findById(idDonacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("La donación no existe"));

        Optional<Factura> facturaExistente = facturaRepository.findByDonacionId(idDonacion);

        if (facturaExistente.isPresent()) {
            throw new ReglaNegocioException("La donación ya tiene una factura asociada", "DONACION");
        }

        Factura factura = new Factura();
        factura.setDonacion(donacion);
        factura.setFecha(LocalDate.now());
        factura.setTipo(TipoFactura.A);

        Factura facturaGuardada = facturaRepository.save(factura);

        return FacturaMapper.toResponse(facturaGuardada);
    }
}