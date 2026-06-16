package com.group6.BancoAlimentos.Features.Producto.repository;

import com.group6.BancoAlimentos.Features.Producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
