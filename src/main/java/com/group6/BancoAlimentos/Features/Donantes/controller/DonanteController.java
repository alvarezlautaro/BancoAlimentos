package com.group6.BancoAlimentos.Features.Donantes.controller;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.service.IDonanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donantes")
public class DonanteController {

    private final IDonanteService donanteService;

    @PreAuthorize("hasAuthority('EMPRESA_VER')")
    @GetMapping
    public ResponseEntity<List<DonanteResponseDTO>> findAllDonantes() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(donanteService.findAll());

    }

    @PreAuthorize("hasAuthority('EMPRESA_VER')")
    @GetMapping("/{id}")
    public ResponseEntity<DonanteResponseDTO> findDonanteById(@PathVariable Long id) {
        DonanteResponseDTO responseDTO = donanteService.findById(id).orElseThrow(() -> new RuntimeException("El donante no existe"));
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAuthority('EMPRESA_CREAR')")
    @PostMapping
    public ResponseEntity<DonanteResponseDTO> create(@Valid @RequestBody DonanteRequestDTO donante){
        return ResponseEntity.status(HttpStatus.CREATED).body(donanteService.save(donante));
    }

    @PreAuthorize("hasAuthority('EMPRESA_ACTUALIZAR')")
    @PutMapping("/{id}")
    public ResponseEntity<DonanteResponseDTO> updateDonante(@PathVariable Long id ,
                                                              @RequestBody @Valid DonanteRequestDTO donanteRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(donanteService.update(id,donanteRequestDTO));

    }

    @PreAuthorize("hasAuthority('EMPRESA_ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonanteById(@PathVariable Long id){
        donanteService.delete(id);
        return ResponseEntity.ok("Donante eliminado");
    }

}
