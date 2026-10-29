package com.group6.BancoAlimentos.Features.Remito;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface IRemitoRepositorio extends JpaRepository<Remito, Long>, JpaSpecificationExecutor<Remito> {
    boolean existsByExternalId(UUID externalId);
    void deleteByExternalId(UUID externalId);
    Optional<Remito> findByExternalId(UUID externalId);
    Page<Remito> findAll(Specification<Remito> spec, Pageable pageable);
}
