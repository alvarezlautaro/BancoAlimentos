package com.group6.BancoAlimentos.Common.mapper;

public interface IActualizarMapper<T, R> extends IMapper<T, R> {
    T actualizarEntidad(R DTO, T entidadExistente);
}
