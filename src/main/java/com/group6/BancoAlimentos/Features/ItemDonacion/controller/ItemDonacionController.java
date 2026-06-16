package com.group6.BancoAlimentos.Features.ItemDonacion.controller;

import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionRequestDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.dto.ItemDonacionResponseDTO;
import com.group6.BancoAlimentos.Features.ItemDonacion.service.IItemDonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemdonacion")
@RequiredArgsConstructor
public class ItemDonacionController {

    private final IItemDonacionService itemDonacionService;



    @GetMapping
    public ResponseEntity<List<ItemDonacionResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(itemDonacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDonacionResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findById(id));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByProductoID(@PathVariable Long productoId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByProductoID(productoId));
    }

    @GetMapping("/donacion/{donacionId}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByDonacionID(@PathVariable Long donacionId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByDonacionID(donacionId));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ItemDonacionResponseDTO>> findByCategoria(@PathVariable String categoria) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.findByCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDonacionResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ItemDonacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDonacionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        itemDonacionService.delete(id);
        return ResponseEntity.ok("ItemDonacion eliminado");
    }
}
