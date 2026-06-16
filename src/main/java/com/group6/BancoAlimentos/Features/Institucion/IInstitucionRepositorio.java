package com.group6.BancoAlimentos.Features.Institucion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IInstitucionRepositorio extends JpaRepository<Institucion, Long> {
    Optional<Institucion> findByNombre(String nombre);
    List<Institucion> findByTipo(tipoInstitucion tipo);
    List<Institucion> findByEstado(estadoPago estado);
    Page<Institucion> findAll(Pageable pageable);
    Page<Institucion> findByTipo(tipoInstitucion tipo, Pageable pageable);
    Page<Institucion> findByEstado(estadoPago estado, Pageable pageable);
}
