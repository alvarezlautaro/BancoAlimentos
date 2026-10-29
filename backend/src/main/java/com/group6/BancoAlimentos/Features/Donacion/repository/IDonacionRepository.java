package com.group6.BancoAlimentos.Features.Donacion.repository;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDonacionRepository extends JpaRepository<Donacion,Long> {
}
