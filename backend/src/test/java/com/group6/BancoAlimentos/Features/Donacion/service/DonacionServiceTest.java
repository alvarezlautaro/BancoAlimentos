package com.group6.BancoAlimentos.Features.Donacion.service;

import com.group6.BancoAlimentos.Features.Donacion.model.Donacion;
import com.group6.BancoAlimentos.Features.Donacion.model.EstadoDonacion;
import com.group6.BancoAlimentos.Features.Donacion.repository.IDonacionRepository;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.Donantes.repository.IDonanteRepository;
import com.group6.BancoAlimentos.Features.Factura.model.Factura;
import com.group6.BancoAlimentos.Features.Factura.model.TipoFactura;
import com.group6.BancoAlimentos.Features.Factura.repository.IFacturaRepository;
import com.group6.BancoAlimentos.Features.Producto.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonacionServiceTest {

    @Mock
    private IDonacionRepository donacionRepository;
    @Mock
    private IDonanteRepository donanteRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private IFacturaRepository facturaRepository;

    private DonacionService service;

    @BeforeEach
    void setUp() {
        service = new DonacionService(
                donacionRepository,
                donanteRepository,
                productoRepository,
                facturaRepository
        );
    }

    @Test
    void confirmarDonacionCreaUnaFacturaSiTodaviaNoExiste() {
        Donacion donacion = donacion();
        when(donacionRepository.findById(7L)).thenReturn(Optional.of(donacion));
        when(donacionRepository.save(donacion)).thenReturn(donacion);
        when(facturaRepository.findByDonacionId(7L)).thenReturn(Optional.empty());

        service.confirmarDonacion(7L);

        ArgumentCaptor<Factura> factura = ArgumentCaptor.forClass(Factura.class);
        verify(facturaRepository).save(factura.capture());
        assertEquals(donacion, factura.getValue().getDonacion());
        assertEquals(TipoFactura.C, factura.getValue().getTipo());
        assertEquals(LocalDate.now(), factura.getValue().getFecha());
    }

    @Test
    void confirmarDonacionNoDuplicaUnaFacturaExistente() {
        Donacion donacion = donacion();
        when(donacionRepository.findById(7L)).thenReturn(Optional.of(donacion));
        when(donacionRepository.save(donacion)).thenReturn(donacion);
        when(facturaRepository.findByDonacionId(7L)).thenReturn(Optional.of(new Factura()));

        service.confirmarDonacion(7L);

        verify(facturaRepository, never()).save(any(Factura.class));
    }

    private Donacion donacion() {
        Donante donante = new Donante();
        donante.setId(3L);
        donante.setRazonSocial("Donante");

        Donacion donacion = new Donacion();
        donacion.setId(7L);
        donacion.setEstado(EstadoDonacion.PENDIENTE);
        donacion.setDonante(donante);
        donacion.setItemDonaciones(List.of());
        return donacion;
    }
}
