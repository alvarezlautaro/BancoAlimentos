package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IInstitucionRepositorio extends JpaRepository<Institucion, Long> {
    Optional<Institucion> encontrarPorId(Long id);
}
