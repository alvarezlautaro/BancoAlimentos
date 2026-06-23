package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Features.Remito.Remito;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "institucion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Institucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institucion")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tipoInstitucion tipo;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private estadoPago estado;

    @NotAudited
    @OneToMany(mappedBy = "institucion", orphanRemoval = true)
    private List<Remito> remitos;

    @PrePersist
    protected void onCreate() {
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }
}
