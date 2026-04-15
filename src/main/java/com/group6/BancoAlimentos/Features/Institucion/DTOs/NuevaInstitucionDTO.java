package com.group6.BancoAlimentos.Features.Institucion.DTOs;

import com.group6.BancoAlimentos.Features.Institucion.tipoInstitucion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuevaInstitucionDTO(
        @NotBlank String nombre,

        @NotNull tipoInstitucion tipo,

        @NotBlank String direccion,

        String telefono,

        @Email String email

        //Al ser todos de ingreso por el cliente/usuario tienen verificaciones
) {}
