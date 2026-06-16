package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Features.Remito.DTOs.ActualizarRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Remitos", description = "Gestión de remitos en Banco de Alimentos")
@RequestMapping("/api/remitos")
public class RemitoController {
    private final RemitoServicio remitoServicio;

    @Operation(summary = "Crear un nuevo remito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Remito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el request"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada"),
            @ApiResponse(responseCode = "422", description = "Violación de regla de negocio")
    })
    @PostMapping
    public ResponseEntity<RemitoDTO> create(@Valid @RequestBody NuevoRemitoDTO nuevoRemito){
        return ResponseEntity.status(HttpStatus.CREATED).body(remitoServicio.crear(nuevoRemito));
    }

    @Operation(summary = "Obtener todos los remitos paginados", description = "Permite filtrar por mes, año o rango de fechas. Parámetros: page, size, sort")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de remitos obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de fecha o paginación inválidos")
    })
    @GetMapping
    public ResponseEntity<Page<RemitoDTO>> encontrarTodos(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            @PageableDefault(size = 10, sort = "fecha") Pageable pageable){
        return ResponseEntity.ok(remitoServicio.encontrarTodos(mes, anio, desde, hasta, pageable));
    }

    @Operation(summary = "Obtener un remito por su id externo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Remito encontrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El id externo no es un UUID válido"),
            @ApiResponse(responseCode = "404", description = "Remito no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RemitoDTO> encontrarPorExternalId(@PathVariable UUID id){
        return ResponseEntity.ok(remitoServicio.encontrarPorExternalID(id));
    }

    @Operation(summary = "Actualizar un remito completo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Remito actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el request"),
            @ApiResponse(responseCode = "404", description = "Remito o institución no encontrada"),
            @ApiResponse(responseCode = "422", description = "Violación de regla de negocio")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RemitoDTO> actualizar(@PathVariable UUID id, @RequestBody ActualizarRemitoDTO actualizarRemito){
        return ResponseEntity.ok(remitoServicio.actualizar(id, actualizarRemito));
    }

    @Operation(summary = "Actualizar parcialmente un remito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Remito actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el request"),
            @ApiResponse(responseCode = "404", description = "Remito o institución no encontrada"),
            @ApiResponse(responseCode = "422", description = "Violación de regla de negocio")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<RemitoDTO> actualizacionParcial(@PathVariable UUID id, @RequestBody ActualizarRemitoDTO actualizarRemito){
        return ResponseEntity.ok(remitoServicio.actualizacionParcial(id, actualizarRemito));
    }

    @Operation(summary = "Eliminar un remito por su id externo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Remito eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El id externo no es un UUID válido"),
            @ApiResponse(responseCode = "404", description = "Remito no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        remitoServicio.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
