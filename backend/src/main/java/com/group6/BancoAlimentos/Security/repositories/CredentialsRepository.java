package com.group6.BancoAlimentos.Security.repositories;

import com.group6.BancoAlimentos.Security.entities.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {
    Optional<CredentialsEntity> findByUsername(String username);
}
