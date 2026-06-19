package com.group6.BancoAlimentos.Features.Producto.repository;

import com.group6.BancoAlimentos.Features.Producto.emun.Categoria;
import com.group6.BancoAlimentos.Features.Producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    List<Producto> findByCategoria(Categoria categoria);
}
