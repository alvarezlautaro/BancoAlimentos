package com.group6.BancoAlimentos.Features.ItemDonacion.controller;

import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.service.IItemDonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemdonacion")
@RequiredArgsConstructor
public class ItemDonacionController {

    private final IItemDonacionService itemDonacionService;



    @PreAuthorize("hasAuthority('ITEM_DONACION_VER')")
    @GetMapping
    public ResponseEntity<List<ItemDonacionResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(itemDonacionService.findAll());
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_VER')")
    @GetMapping("/{id}")
    public ResponseEntity<ItemDonacionResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findById(id));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_VER')")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByProductoID(@PathVariable Long productoId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByProductoID(productoId));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_VER')")
    @GetMapping("/donacion/{donacionId}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByDonacionID(@PathVariable Long donacionId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByDonacionID(donacionId));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_VER')")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByCategoria(@PathVariable String categoria) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByCategoria(categoria));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_CREAR')")
    @PostMapping
    public ResponseEntity<ItemDonacionResponseDTO> create(@RequestBody @Valid ItemDonacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDonacionService.save(dto));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_ACTUALIZAR')")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDonacionResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ItemDonacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_ACTUALIZAR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ItemDonacionResponseDTO> partialUpdate(@PathVariable Long id, @RequestBody @Valid ItemDonacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('ITEM_DONACION_ELIMINAR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        itemDonacionService.delete(id);
        return ResponseEntity.ok("ItemDonacion eliminado");
    }
}
