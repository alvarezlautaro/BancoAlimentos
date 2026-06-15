package com.group6.BancoAlimentos.Features.Donacion.controller;

import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;
import com.group6.BancoAlimentos.Features.Donacion.service.IDonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final IDonacionService donacionService;

    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> findAll() {
        return ResponseEntity.ok(donacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> findById(@PathVariable Long id) {
        return donacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("La donación no existe"));
    }

    @PostMapping
    public ResponseEntity<DonacionResponseDTO> save(@Valid @RequestBody DonacionRequestDTO dto) {
        DonacionResponseDTO donacionGuardada = donacionService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(donacionGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DonacionRequestDTO dto
    ) {
        return ResponseEntity.ok(donacionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        donacionService.delete(id);

        return ResponseEntity.ok("Donación eliminada correctamente");
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<DonacionResponseDTO> confirmarDonacion(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.confirmarDonacion(id));
    }
}