package com.group6.BancoAlimentos.Features.Institucion.Mapper;

public interface IMapper<T, R>{
    T toEntity(R r);
    R toDTO(T t);
}
