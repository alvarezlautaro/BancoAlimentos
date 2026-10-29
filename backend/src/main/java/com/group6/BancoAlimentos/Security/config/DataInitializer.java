package com.group6.BancoAlimentos.Security.config;

import com.group6.BancoAlimentos.Security.entities.CredentialsEntity;
import com.group6.BancoAlimentos.Security.entities.PermitEntity;
import com.group6.BancoAlimentos.Security.entities.RoleEntity;
import com.group6.BancoAlimentos.Security.enums.Permits;
import com.group6.BancoAlimentos.Security.enums.Roles;
import com.group6.BancoAlimentos.Security.repositories.CredentialsRepository;
import com.group6.BancoAlimentos.Security.repositories.PermitRepository;
import com.group6.BancoAlimentos.Security.repositories.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermitRepository permitRepository;
    private final RoleRepository roleRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        credentialsRepository.deleteAll();
        entityManager.flush();
        roleRepository.deleteAll();
        entityManager.flush();
        permitRepository.deleteAll();
        entityManager.flush();

        PermitEntity empresaCrear = permitRepository.save(PermitEntity.builder().permit(Permits.EMPRESA_CREAR).build());
        PermitEntity empresaVer = permitRepository.save(PermitEntity.builder().permit(Permits.EMPRESA_VER).build());
        PermitEntity empresaActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.EMPRESA_ACTUALIZAR).build());
        PermitEntity empresaEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.EMPRESA_ELIMINAR).build());
        PermitEntity donacionCrear = permitRepository.save(PermitEntity.builder().permit(Permits.DONACION_CREAR).build());
        PermitEntity donacionVer = permitRepository.save(PermitEntity.builder().permit(Permits.DONACION_VER).build());
        PermitEntity donacionActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.DONACION_ACTUALIZAR).build());
        PermitEntity donacionEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.DONACION_ELIMINAR).build());
        PermitEntity productoC = permitRepository.save(PermitEntity.builder().permit(Permits.PRODUCTO_CREAR).build());
        PermitEntity productoVer = permitRepository.save(PermitEntity.builder().permit(Permits.PRODUCTO_VER).build());
        PermitEntity productoActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.PRODUCTO_ACTUALIZAR).build());
        PermitEntity productoEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.PRODUCTO_ELIMINAR).build());
        PermitEntity itemDonacionCrear = permitRepository.save(PermitEntity.builder().permit(Permits.ITEM_DONACION_CREAR).build());
        PermitEntity itemDonacionVer = permitRepository.save(PermitEntity.builder().permit(Permits.ITEM_DONACION_VER).build());
        PermitEntity itemDonacionActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.ITEM_DONACION_ACTUALIZAR).build());
        PermitEntity itemDonacionEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.ITEM_DONACION_ELIMINAR).build());
        PermitEntity institucionCrear = permitRepository.save(PermitEntity.builder().permit(Permits.INSTITUCION_CREAR).build());
        PermitEntity institucionVer = permitRepository.save(PermitEntity.builder().permit(Permits.INSTITUCION_VER).build());
        PermitEntity institucionActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.INSTITUCION_ACTUALIZAR).build());
        PermitEntity institucionEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.INSTITUCION_ELIMINAR).build());
        PermitEntity estadoPagoModificar = permitRepository.save(PermitEntity.builder().permit(Permits.ESTADO_PAGO_MODIFICAR).build());
        PermitEntity remitoCrear = permitRepository.save(PermitEntity.builder().permit(Permits.REMITO_CREAR).build());
        PermitEntity remitoVer = permitRepository.save(PermitEntity.builder().permit(Permits.REMITO_VER).build());
        PermitEntity remitoActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.REMITO_ACTUALIZAR).build());
        PermitEntity remitoEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.REMITO_ELIMINAR).build());
        PermitEntity detalleRemitoCrear = permitRepository.save(PermitEntity.builder().permit(Permits.DETALLE_REMITO_CREAR).build());
        PermitEntity detalleRemitoVer = permitRepository.save(PermitEntity.builder().permit(Permits.DETALLE_REMITO_VER).build());
        PermitEntity detalleRemitoActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.DETALLE_REMITO_ACTUALIZAR).build());
        PermitEntity detalleRemitoEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.DETALLE_REMITO_ELIMINAR).build());
        PermitEntity facturaCrear = permitRepository.save(PermitEntity.builder().permit(Permits.FACTURA_CREAR).build());
        PermitEntity facturaVer = permitRepository.save(PermitEntity.builder().permit(Permits.FACTURA_VER).build());
        PermitEntity facturaActualizar = permitRepository.save(PermitEntity.builder().permit(Permits.FACTURA_ACTUALIZAR).build());
        PermitEntity facturaEliminar = permitRepository.save(PermitEntity.builder().permit(Permits.FACTURA_ELIMINAR).build());
        PermitEntity auditoriaDonacionVer = permitRepository.save(PermitEntity.builder().permit(Permits.AUDITORIA_DONACION_VER).build());
        PermitEntity auditoriaInstitucionVer = permitRepository.save(PermitEntity.builder().permit(Permits.AUDITORIA_INSTITUCION_VER).build());

        RoleEntity roleDeposito = roleRepository.save(RoleEntity.builder()
                .role(Roles.USER_DEPOSITO)
                .permits(new HashSet<>(Set.of(
                        empresaCrear, empresaVer, empresaActualizar, empresaEliminar,
                        donacionCrear, donacionVer, donacionActualizar, donacionEliminar,
                        productoC, productoVer, productoActualizar, productoEliminar,
                        itemDonacionCrear, itemDonacionVer, itemDonacionActualizar, itemDonacionEliminar,
                        institucionVer,
                        remitoCrear, remitoVer, remitoActualizar, remitoEliminar,
                        detalleRemitoCrear, detalleRemitoVer, detalleRemitoActualizar, detalleRemitoEliminar,
                        facturaCrear, facturaVer, facturaActualizar, facturaEliminar,
                        auditoriaDonacionVer
                )))
                .build());

        RoleEntity roleTesoreria = roleRepository.save(RoleEntity.builder()
                .role(Roles.USER_TESORERIA)
                .permits(new HashSet<>(Set.of(institucionVer, estadoPagoModificar)))
                .build());

        RoleEntity roleInstitucional = roleRepository.save(RoleEntity.builder()
                .role(Roles.USER_INSTITUCIONAL)
                .permits(new HashSet<>(Set.of(
                        institucionCrear, institucionVer, institucionActualizar, institucionEliminar,
                        auditoriaInstitucionVer
                )))
                .build());

        credentialsRepository.save(CredentialsEntity.builder()
                .username("deposito")
                .password(passwordEncoder.encode("deposito123"))
                .enabled(true)
                .roles(new HashSet<>(Set.of(roleDeposito)))
                .build());

        credentialsRepository.save(CredentialsEntity.builder()
                .username("tesoreria")
                .password(passwordEncoder.encode("tesoreria123"))
                .enabled(true)
                .roles(new HashSet<>(Set.of(roleTesoreria)))
                .build());

        credentialsRepository.save(CredentialsEntity.builder()
                .username("institucional")
                .password(passwordEncoder.encode("institucional123"))
                .enabled(true)
                .roles(new HashSet<>(Set.of(roleInstitucional)))
                .build());
    }
}
