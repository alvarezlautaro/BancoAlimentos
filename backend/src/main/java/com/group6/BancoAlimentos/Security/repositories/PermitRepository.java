package com.group6.BancoAlimentos.Security.repositories;

import com.group6.BancoAlimentos.Security.entities.PermitEntity;
import com.group6.BancoAlimentos.Security.enums.Permits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermitRepository extends JpaRepository<PermitEntity, Long> {
    Optional<PermitEntity> findByPermit(Permits permit);
}
