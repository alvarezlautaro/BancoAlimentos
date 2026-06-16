package com.group6.BancoAlimentos.Features.DetalleRemito;

import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoRequest;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoResponse;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "detalle-remitos", description = "Gestión de detalle remitos en Banco de Alimentos")
@RequestMapping("/api/detalles-remito")
public class DetalleRemitoController {
    private final DetalleRemitoServicio detalleRemitoServicio;

    @Operation(summary = "Obtener todos los detalles de remito paginados", description = "Parámetros: page, size, sort")
    @ApiResponse(responseCode = "200", description = "Página de detalles obtenida exitosamente")
    @GetMapping
    public ResponseEntity<Page<DetalleRemitoResponse>> encontrarTodos(
            @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(detalleRemitoServicio.encontrarTodos(pageable));
    }

    @Operation(summary = "Obtener detalles por id de remito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Remito no encontrado")
    })
    @GetMapping("/remito/{idRemito}")
    public ResponseEntity<List<DetalleRemitoResponse>> encontrarPorIdRemito(@PathVariable Long idRemito){
        return ResponseEntity.ok(detalleRemitoServicio.encontrarPorIdRemito(idRemito));
    }

    @Operation(summary = "Obtener detalles por id de item donación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item donación no encontrado")
    })
    @GetMapping("/item-donacion/{idItemDonacion}")
    public ResponseEntity<List<DetalleRemitoResponse>> encontrarPorItemDonacion(@PathVariable Long idItemDonacion){
        return ResponseEntity.ok(detalleRemitoServicio.encontrarPorItemDonacion(idItemDonacion));
    }

    @Operation(summary = "Crear un nuevo detalle de remito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el request"),
            @ApiResponse(responseCode = "404", description = "Remito o item donación no encontrado"),
            @ApiResponse(responseCode = "422", description = "Violación de regla de negocio")
    })
    @PostMapping
    public ResponseEntity<DetalleRemitoResponse> crear(@Valid @RequestBody DetalleRemitoRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleRemitoServicio.crear(dto));
    }

    @Operation(summary = "Actualizar un detalle de remito por su id externo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en el request o id externo no coincide"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "422", description = "Violación de regla de negocio")
    })
    @PutMapping("/{externalId}")
    public ResponseEntity<DetalleRemitoResponse> actualizar(@PathVariable UUID externalId, @Valid @RequestBody DetalleRemitoRequest dto){
        return ResponseEntity.ok(detalleRemitoServicio.actualizar(externalId, dto));
    }

    @Operation(summary = "Eliminar un detalle de remito por su id externo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El id externo no es un UUID válido"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID externalId){
        detalleRemitoServicio.eliminar(externalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}