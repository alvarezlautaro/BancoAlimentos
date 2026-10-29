package com.group6.BancoAlimentos.Features.Donantes.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonanteResponseDTO {

    private String razonSocial;
    private String cuit;
    private String telefono;
    private String email;
    private String direccion;
}
