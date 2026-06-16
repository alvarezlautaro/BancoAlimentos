package com.group6.BancoAlimentos.Features.ItemDonacion.repository;

import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDonacionRepository extends JpaRepository<ItemDonacion,Long> {
}
