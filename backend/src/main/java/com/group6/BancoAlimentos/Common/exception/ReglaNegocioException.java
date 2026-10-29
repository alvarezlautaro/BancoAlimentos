package com.group6.BancoAlimentos.Common.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReglaNegocioException extends RuntimeException {
    private final String categoria;
    private final LocalDateTime timestamp;

    public ReglaNegocioException(String message, String categoria) {
        super(message);
        this.categoria = categoria;
        this.timestamp = LocalDateTime.now();
    }
}
