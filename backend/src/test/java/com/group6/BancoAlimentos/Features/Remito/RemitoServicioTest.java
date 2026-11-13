package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import com.group6.BancoAlimentos.Features.DetalleRemito.IDetalleRemitoRepositorio;
import com.group6.BancoAlimentos.Features.Institucion.IInstitucionRepositorio;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import com.group6.BancoAlimentos.Features.Institucion.estadoPago;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.repository.ItemDonacionRepository;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoItemRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Mapper.ActualizarRemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoNuevoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemitoServicioTest {

    @Mock
    private IRemitoRepositorio remitoRepositorio;
    @Mock
    private IInstitucionRepositorio institucionRepositorio;
    @Mock
    private ItemDonacionRepository itemDonacionRepositorio;
    @Mock
    private IDetalleRemitoRepositorio detalleRemitoRepositorio;
    @Mock
    private RemitoMapper remitoMapper;
    @Mock
    private RemitoNuevoMapper remitoNuevoMapper;
    @Mock
    private ActualizarRemitoMapper actualizarRemitoMapper;

    private RemitoServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new RemitoServicio(
                remitoRepositorio,
                institucionRepositorio,
                itemDonacionRepositorio,
                detalleRemitoRepositorio,
                remitoMapper,
                remitoNuevoMapper,
                actualizarRemitoMapper
        );
    }

    @Test
    void crearRemitoRegistraDetallesYActualizaElStockDelItem() {
        LocalDate fecha = LocalDate.now();
        NuevoRemitoDTO request = new NuevoRemitoDTO(
                fecha,
                3L,
                List.of(new NuevoItemRemitoDTO(8L, 2))
        );
        Institucion institucion = new Institucion();
        institucion.setEstado(estadoPago.AL_DIA);
        Remito remito = new Remito();
        remito.setFecha(fecha);
        ItemDonacion item = new ItemDonacion();
        item.setId(8L);
        item.setCantidad(5);
        item.setFechaVencimiento(Date.from(fecha.plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(remitoNuevoMapper.aEntidad(request)).thenReturn(remito);
        when(institucionRepositorio.findById(3L)).thenReturn(Optional.of(institucion));
        when(remitoRepositorio.save(any(Remito.class))).thenAnswer(invocation -> {
            Remito guardado = invocation.getArgument(0);
            guardado.setId(10L);
            return guardado;
        });
        when(itemDonacionRepositorio.findById(8L)).thenReturn(Optional.of(item));
        when(remitoMapper.aDTO(remito)).thenReturn(new RemitoDTO());

        servicio.crear(request);

        assertEquals(3, item.getCantidad());
        verify(itemDonacionRepositorio).save(item);
        verify(detalleRemitoRepositorio).save(any(DetalleRemito.class));
        verify(remitoRepositorio, times(2)).save(remito);
    }
}
