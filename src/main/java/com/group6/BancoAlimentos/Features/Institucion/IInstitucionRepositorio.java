package com.group6.BancoAlimentos.Features.Institucion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IInstitucionRepositorio extends JpaRepository<Institucion, Long> {
    Optional<Institucion> findByNombre(String nombre);
    List<Institucion> findByTipo(tipoInstitucion tipo);
    List<Institucion> findByEstadoPago(estadoPago estado);

    tipoInstitucion tipo(tipoInstitucion tipo);
}
