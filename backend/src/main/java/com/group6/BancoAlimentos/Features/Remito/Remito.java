package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "remito")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Remito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_remito")
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_institucion")
    private Institucion institucion;

    @OneToMany(mappedBy = "remito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleRemito> detalles = new ArrayList<>();
}
