package com.group6.BancoAlimentos.Features.Donantes.repository;

import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDonanteRepository extends JpaRepository<Donante, Long> {

    @Override
    boolean existsByCuit(Long cuit);
}
