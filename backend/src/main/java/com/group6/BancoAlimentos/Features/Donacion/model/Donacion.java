package com.group6.BancoAlimentos.Features.Donacion.model;

import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donacion")
    private Long id;

    @Column(name = "fecha",nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDonacion estado;

    @Column(name = "nro_remito_proveedor")
    private int nroRemitoProveedor;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "donacion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemDonacion> itemDonaciones;

    @ManyToOne
    @JoinColumn(name = "id_donante", nullable = false)
    private Donante donante;

}
