package com.group6.BancoAlimentos.Features.Institucion.Mapper;

public interface IMapper<T, R>{
    T aEntidad(R r);
    R aDTO(T t);
}
