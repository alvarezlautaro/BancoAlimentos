package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionParcialDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Instituciones", description = "Gestión de instituciones del Banco de Alimentos")
@RequestMapping("/api/instituciones")
public class InstitucionController {
    private final InstitucionServicio institucionServicio;

    @PreAuthorize("hasAnyAuthority('ROLE_USER_DEPOSITO', 'ROLE_USER_TESORERIA', 'ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Obtener todas las instituciones paginadas", description = "Parámetros: page, size, sort"
    )
    @ApiResponse(responseCode = "200", description = "Página de instituciones obtenida correctamente")
    @GetMapping
    public ResponseEntity<Page<InstitucionDTO>> encontrarTodos(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(institucionServicio.encontrarTodos(pageable));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_DEPOSITO', 'ROLE_USER_TESORERIA', 'ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Obtener una institución por un id dado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InstitucionDTO> encontrarPorID(@PathVariable UUID externalId){
        return ResponseEntity.ok(institucionServicio.encontrarPorID(externalId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_DEPOSITO', 'ROLE_USER_TESORERIA', 'ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Obtener una institución por un nombre dado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<InstitucionDTO> encontrarPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(institucionServicio.encontrarPorNombre(nombre));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_DEPOSITO', 'ROLE_USER_TESORERIA', 'ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Obtener instituciones por tipo paginadas", description = "Parámetros: page, size, sort"
    )
    @ApiResponse(responseCode = "200", description = "Página de instituciones obtenida correctamente")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Page<InstitucionDTO>> encontrarPorTipo(
            @PathVariable tipoInstitucion tipo,
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(institucionServicio.encontrarPorTipo(tipo, pageable));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_DEPOSITO', 'ROLE_USER_TESORERIA', 'ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Obtener instituciones por estado de pago paginadas", description = "Parámetros: page, size, sort"
    )
    @ApiResponse(responseCode = "200", description = "Página de instituciones obtenida correctamente")
    @GetMapping("/estado-pago/{estado}")
    public ResponseEntity<Page<InstitucionDTO>> encontrarPorEstadoPago(
            @PathVariable estadoPago estado,
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable){
        return ResponseEntity.ok(institucionServicio.encontrarPorEstadoPago(estado, pageable));
    }

    @PreAuthorize("hasAuthority('ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Crear una nueva institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Institución creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos")
    })
    @PostMapping
    public ResponseEntity<InstitucionDTO> crear(@Valid @RequestBody NuevaInstitucionDTO nuevaInstitucionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(institucionServicio.guardar(nuevaInstitucionDTO));
    }

    @PreAuthorize("hasAuthority('ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Actualización completa de una institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @PutMapping("/{externalId}")
    public ResponseEntity<InstitucionDTO> actualizar(@PathVariable UUID externalId, @Valid @RequestBody InstitucionDTO institucionDTO){
        return ResponseEntity.ok(institucionServicio.actualizar(externalId, institucionDTO));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_INSTITUCIONAL', 'ROLE_USER_TESORERIA')")
    @Operation(
            summary = "Actualización parcial de una institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @PatchMapping("/{externalId}")
    public ResponseEntity<InstitucionDTO> actualizacionParcial(@PathVariable UUID externalId, @Valid @RequestBody ActualizarInstitucionParcialDTO dtoParcial){
        return ResponseEntity.ok(institucionServicio.actualizacionParcial(externalId, dtoParcial));
    }

    @PreAuthorize("hasAuthority('ROLE_USER_INSTITUCIONAL')")
    @Operation(
            summary = "Eliminar una institución por id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Institución eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID externalId){
        institucionServicio.eliminar(externalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}