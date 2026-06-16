package com.group6.BancoAlimentos.Features.ItemDonacion.repository;

import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDonacionRepository extends JpaRepository<ItemDonacion,Long> {
    List<ItemDonacion> findByProducto_Id(Long productoId);
    List<ItemDonacion> findByDonacion_Id(Long donacionId);
    List<ItemDonacion> findByProducto_Categoria(String categoria);
}
