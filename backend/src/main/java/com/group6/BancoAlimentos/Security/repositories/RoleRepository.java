package com.group6.BancoAlimentos.Security.repositories;

import com.group6.BancoAlimentos.Security.entities.RoleEntity;
import com.group6.BancoAlimentos.Security.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(Roles role);
}
