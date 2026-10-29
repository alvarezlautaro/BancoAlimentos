package com.group6.BancoAlimentos.Features.Factura.repository;

import com.group6.BancoAlimentos.Features.Factura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByDonacionId(Long idDonacion);
}