package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Remito.Remito;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "institucion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Institucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institucion")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalId;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private tipoInstitucion tipo;

    private String direccion;

    private String telefono;

    private String email;

    @Enumerated(EnumType.STRING)
    private estadoPago estado;

    @OneToMany(mappedBy = "institucion", orphanRemoval = true)
    private List<Remito> remitos;

    @PrePersist
    protected void onCreate() {
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }
}
