package com.group6.BancoAlimentos.Features.Institucion.Mapper;

public interface IActualizarMapper<T, R> extends IMapper<T, R> {
    T actualizarEntidad(R DTO, T entidadExistente);
}
