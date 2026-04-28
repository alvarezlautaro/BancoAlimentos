package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Instituciones", description = "Gestión de instituciones del Banco de Alimentos")
@RequestMapping("/api/instituciones")
public class InstitucionController {
    private final InstitucionServicio institucionServicio;

    @Operation(
            summary = "Obtener todas las instituciones"
    )
    @ApiResponse(responseCode = "200", description = "Lista de instituciones obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<InstitucionDTO>> encontrarTodos(){
        return ResponseEntity.ok(institucionServicio.encontrarTodos());
    }

    @Operation(
            summary = "Obtener una institución por un id dado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InstitucionDTO> encontrarPorID(@PathVariable Long id){
        return ResponseEntity.ok(institucionServicio.encontrarPorID(id));
    }

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

    @Operation(
            summary = "Crear una nueva institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Institución creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos")
    })
    @PostMapping
    public ResponseEntity<NuevaInstitucionDTO> crear(@Valid @RequestBody NuevaInstitucionDTO nuevaInstitucionDTO){
        institucionServicio.guardar(nuevaInstitucionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInstitucionDTO);
    }

    @Operation(
            summary = "Actualización completa de una institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InstitucionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InstitucionDTO institucionDTO){
        return ResponseEntity.ok(institucionServicio.actualizar(id, institucionDTO));
    }

    @Operation(
            summary = "Actualización parcial de una institución"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Institución actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos ingresados inválidos"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<InstitucionDTO> actualizacionParcial(@PathVariable Long id, @Valid @RequestBody ActualizarInstitucionDTO dtoParcial){
        return ResponseEntity.ok(institucionServicio.actualizacionParcial(id, dtoParcial));
    }

    @Operation(
            summary = "Eliminar una institución por id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Institución eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Institución no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        institucionServicio.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
