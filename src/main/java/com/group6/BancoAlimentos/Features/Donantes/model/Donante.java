package com.group6.BancoAlimentos.Features.Donantes.model;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name="donantes")
@Audited
public class Donante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String razonSocial;
    @Column(nullable = false)
    private String cuit;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String direccion;


    @NotAudited
    @OneToMany(mappedBy = "donante")
    private List<Donacion> donaciones;

    @PrePersist
    protected void onCreate() {
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }

}
