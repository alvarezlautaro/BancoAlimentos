package com.group6.BancoAlimentos.Features.Donacion.controller;

import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionRequestDTO;
import com.group6.BancoAlimentos.Features.Donacion.dto.DonacionResponseDTO;
import com.group6.BancoAlimentos.Features.Donacion.service.IDonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final IDonacionService donacionService;

    @PreAuthorize("hasAuthority('DONACION_VER')")
    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> findAll() {
        return ResponseEntity.ok(donacionService.findAll());
    }

    @PreAuthorize("hasAuthority('DONACION_VER')")
    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> findById(@PathVariable Long id) {
        return donacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("La donación no existe"));
    }

    @PreAuthorize("hasAuthority('DONACION_CREAR')")
    @PostMapping
    public ResponseEntity<DonacionResponseDTO> save(@Valid @RequestBody DonacionRequestDTO dto) {
        DonacionResponseDTO donacionGuardada = donacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionGuardada);
    }

    @PreAuthorize("hasAuthority('DONACION_ACTUALIZAR')")
    @PutMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DonacionRequestDTO dto
    ) {
        return ResponseEntity.ok(donacionService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('DONACION_ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        donacionService.delete(id);
        return ResponseEntity.ok("Donación eliminada correctamente");
    }

    @PreAuthorize("hasAuthority('DONACION_ACTUALIZAR')")
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<DonacionResponseDTO> confirmarDonacion(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.confirmarDonacion(id));
    }
}