package com.group6.BancoAlimentos.Features.Factura.controller;

import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaRequestDTO;
import com.group6.BancoAlimentos.Features.Factura.DTO.FacturaResponseDTO;
import com.group6.BancoAlimentos.Features.Factura.service.IFacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final IFacturaService facturaService;

    @PreAuthorize("hasAuthority('FACTURA_VER')")
    @GetMapping
    public ResponseEntity<List<FacturaResponseDTO>> findAll() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @PreAuthorize("hasAuthority('FACTURA_VER')")
    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> findById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("La factura no existe"));
    }

    @PreAuthorize("hasAuthority('FACTURA_CREAR')")
    @PostMapping
    public ResponseEntity<FacturaResponseDTO> save(@Valid @RequestBody FacturaRequestDTO dto) {
        FacturaResponseDTO facturaGuardada = facturaService.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(facturaGuardada);
    }

    @PreAuthorize("hasAuthority('FACTURA_ACTUALIZAR')")
    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody FacturaRequestDTO dto
    ) {
        return ResponseEntity.ok(facturaService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('FACTURA_ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        facturaService.delete(id);

        return ResponseEntity.ok("Factura eliminada correctamente");
    }

    @PreAuthorize("hasAuthority('FACTURA_CREAR')")
    @PostMapping("/generar/{idDonacion}")
    public ResponseEntity<FacturaResponseDTO> generarFactura(@PathVariable Long idDonacion) {
        FacturaResponseDTO facturaGenerada = facturaService.generarFactura(idDonacion);

        return ResponseEntity.status(HttpStatus.CREATED).body(facturaGenerada);
    }
}
