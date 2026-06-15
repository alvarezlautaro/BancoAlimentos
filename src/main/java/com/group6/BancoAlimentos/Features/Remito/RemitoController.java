package com.group6.BancoAlimentos.Features.Remito;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Remitos", description = "Gestión de remitos en Banco de Alimentos")
@RequestMapping("/api/remitos")
public class RemitoController {
    private final RemitoServicio remitoServicio;

    
}
