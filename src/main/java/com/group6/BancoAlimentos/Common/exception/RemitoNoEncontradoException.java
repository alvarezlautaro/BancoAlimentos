package com.group6.BancoAlimentos.Common.exception;

import java.util.NoSuchElementException;

public class RemitoNoEncontradoException extends NoSuchElementException {
    public RemitoNoEncontradoException(String message) {
        super(message);
    }
}
