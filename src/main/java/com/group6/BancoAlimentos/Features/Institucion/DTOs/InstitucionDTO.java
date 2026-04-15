package com.group6.BancoAlimentos.Features.Institucion.DTOs;

import com.group6.BancoAlimentos.Features.Institucion.tipoInstitucion;

public record InstitucionDTO(String nombre,
                             tipoInstitucion tipo,
                             String telefono,
                             String email) {
}