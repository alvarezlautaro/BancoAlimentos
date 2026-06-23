package com.group6.BancoAlimentos.Common.mapper;

public interface IMapper<T, R>{
    T aEntidad(R r);
    R aDTO(T t);
}
