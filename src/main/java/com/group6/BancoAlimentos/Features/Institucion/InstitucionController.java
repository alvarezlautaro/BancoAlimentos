package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/instituciones")
public class InstitucionController {
    private final InstitucionServicio institucionServicio;

    @GetMapping
    public ResponseEntity<List<InstitucionDTO>> encontrarTodos(){
        return ResponseEntity.ok(institucionServicio.encontrarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitucionDTO> encontrarPorID(@PathVariable Long id){
        return ResponseEntity.ok(institucionServicio.encontrarPorID(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<InstitucionDTO> encontrarPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(institucionServicio.encontrarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<NuevaInstitucionDTO> crear(@Valid @RequestBody NuevaInstitucionDTO nuevaInstitucionDTO){
        institucionServicio.guardar(nuevaInstitucionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInstitucionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstitucionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InstitucionDTO institucionDTO){
        return ResponseEntity.ok(institucionServicio.actualizar(id, institucionDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InstitucionDTO> actualizacionParcial(@PathVariable Long id, @Valid @RequestBody ActualizarInstitucionDTO dtoParcial){
        return ResponseEntity.ok(institucionServicio.actualizacionParcial(id, dtoParcial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        institucionServicio.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
