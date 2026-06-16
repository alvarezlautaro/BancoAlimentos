package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Features.Remito.DTOs.ActualizarRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Remitos", description = "Gestión de remitos en Banco de Alimentos")
@RequestMapping("/api/remitos")
public class RemitoController {
    private final RemitoServicio remitoServicio;

    @PostMapping
    public ResponseEntity<RemitoDTO> create(@Valid @RequestBody NuevoRemitoDTO nuevoRemito){
        return ResponseEntity.status(HttpStatus.CREATED).body(remitoServicio.crear(nuevoRemito));
    }

    @GetMapping
    public ResponseEntity<List<RemitoDTO>> encontrarTodos(@RequestParam(required = false)Integer mes,
                                                          @RequestParam(required = false)Integer anio,
                                                          @RequestParam(required = false)LocalDate desde,
                                                          @RequestParam(required = false)LocalDate hasta){
        return ResponseEntity.ok(remitoServicio.encontrarTodos(mes, anio, desde, hasta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemitoDTO> encontrarPorExternalId(@PathVariable UUID id){
        return ResponseEntity.ok(remitoServicio.encontrarPorExternalID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemitoDTO> actualizar(@PathVariable UUID id, @RequestBody ActualizarRemitoDTO actualizarRemito){
        return ResponseEntity.ok(remitoServicio.actualizar(id, actualizarRemito));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RemitoDTO> actualizacionParcial(@PathVariable UUID id, @RequestBody ActualizarRemitoDTO actualizarRemito){
        return ResponseEntity.ok(remitoServicio.actualizacionParcial(id, actualizarRemito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        remitoServicio.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
