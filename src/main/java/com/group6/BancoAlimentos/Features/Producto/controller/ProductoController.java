package com.group6.BancoAlimentos.Features.Producto.controller;

import com.group6.BancoAlimentos.Features.Producto.dto.ProductoRequestDTO;
import com.group6.BancoAlimentos.Features.Producto.dto.ProductoResponseDTO;
import com.group6.BancoAlimentos.Features.Producto.service.IProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/productos")
    @RequiredArgsConstructor
    public class ProductoController {

        private final IProductoService productoService;


        @GetMapping
        public ResponseEntity<List<ProductoResponseDTO>> findAllProductos() {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(productoService.findAll());

        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductoResponseDTO> findProductoById(@PathVariable Long id) {
            ProductoResponseDTO responseDTO = productoService.findById(id).orElseThrow(() -> new RuntimeException("El producto no existe"));
            return ResponseEntity.ok(responseDTO);
        }

        @PostMapping
        public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO producto){
            return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductoResponseDTO> updateProducto(@PathVariable Long id ,
                                                                  @RequestBody @Valid ProductoRequestDTO productoRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.update(id,productoRequestDTO));

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteProductoById(@PathVariable Long id){
            productoService.delete(id);
            return ResponseEntity.ok("Producto eliminado");
        }


}

