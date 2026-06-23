package com.group6.BancoAlimentos.Features.Donantes.repository;

import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IDonanteRepository extends JpaRepository<Donante, Long> {
    Optional<Donante> findByExternalId(UUID externalId);
    boolean existsByCuit(String cuit);
}
