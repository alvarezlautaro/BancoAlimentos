package com.group6.BancoAlimentos.Features.Institucion;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstitucionServicio implements IInstitucionServicio{
    private final IInstitucionRepositorio institucionRepositorio;

}
