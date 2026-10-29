package com.group6.BancoAlimentos.Common.exception;

import java.util.NoSuchElementException;

public class RecursoNoEncontradoException extends NoSuchElementException {
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
