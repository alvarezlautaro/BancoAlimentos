package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

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

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalId = UUID.randomUUID();

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_institucion")
    private Institucion institucion;
}
