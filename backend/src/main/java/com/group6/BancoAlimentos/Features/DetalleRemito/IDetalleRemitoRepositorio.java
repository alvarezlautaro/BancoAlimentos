package com.group6.BancoAlimentos.Features.DetalleRemito;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IDetalleRemitoRepositorio extends JpaRepository<DetalleRemito, Long> {
    Optional<DetalleRemito> findByExternalId(UUID externalId);

    Page<DetalleRemito> findAll(Pageable pageable);

    List<DetalleRemito> findByRemitoId(Long idRemito);

    List<DetalleRemito> findByItemDonacionId(Long idItemDonacion);

    boolean existsByRemitoIdAndItemDonacionId(Long idRemito, Long idItemDonacion);

    boolean existsByItemDonacion_Id(Long itemDonacionId);

}
