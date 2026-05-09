package com.group6.BancoAlimentos.Common.exception;

import java.util.NoSuchElementException;

public class InstitucionNoEncontradaException extends NoSuchElementException {
    public InstitucionNoEncontradaException(String message) {
        super(message);
    }
}
