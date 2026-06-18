package com.group6.BancoAlimentos.Features.Donantes.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonanteRequestDTO {



    @NotNull(message = "La razon social no puede estar vacia")
    private String razonSocial;
    @Size(min = 11, max = 11, message = "Ingrese un CUIT valido")
    private String cuit;
    @Size(min = 10, max = 11,message = "Ingrese un telefono valido")
    private String telefono;
    @Email
    private String email;
    @NotNull(message = "La direccion no puede estar vacia")
    private String direccion;
}