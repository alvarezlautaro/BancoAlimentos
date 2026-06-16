package com.group6.BancoAlimentos.Features.DetalleRemito;

import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "detalle-remito")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetalleRemito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleRemito;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalId;

    @ManyToOne
    @JoinColumn(name = "id_remito")
    private Remito remito;

    @ManyToOne
    @JoinColumn(name = "id_item_donacion")
    private ItemDonacion itemDonacion;

    @Column(nullable = false)
    private Integer cantidad;

}
