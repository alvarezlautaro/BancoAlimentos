package com.group6.BancoAlimentos.Features.Donacion.service;

import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;
import com.group6.BancoAlimentos.Features.Donacion.mapper.DonacionMapper;
import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import com.group6.BancoAlimentos.Features.Donacion.repository.IDonacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonacionService implements IDonacionService {

    private final IDonacionRepository donacionRepository;

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
        Donacion donacion = DonacionMapper.toEntity(dto);

        Donacion donacionGuardada = donacionRepository.save(donacion);

        return DonacionMapper.toResponse(donacionGuardada);
    }

    @Override
    public DonacionResponseDTO update(Long id, DonacionRequestDTO dto) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La donación no existe"));

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
            throw new RuntimeException("La donación no existe");
        }

        donacionRepository.deleteById(id);
    }

    @Override
    public DonacionResponseDTO confirmarDonacion(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La donación no existe"));

        donacion.setEstado(EstadoDonacion.CONFIRMADA);

        Donacion donacionConfirmada = donacionRepository.save(donacion);

        return DonacionMapper.toResponse(donacionConfirmada);
    }
}