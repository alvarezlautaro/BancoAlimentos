package com.group6.BancoAlimentos.Common.exception;

import java.time.LocalDateTime;

public record ErrorReglaNegocioDTO(
        String mensaje,
        String categoria,
        LocalDateTime timestamp
) {
}
