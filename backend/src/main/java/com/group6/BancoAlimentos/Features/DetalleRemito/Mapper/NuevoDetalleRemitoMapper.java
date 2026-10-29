package com.group6.BancoAlimentos.Features.DetalleRemito.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.NuevoDetalleRemito;
import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NuevoDetalleRemitoMapper implements IMapper<DetalleRemito, NuevoDetalleRemito> {
    private final ModelMapper modelMapper;

    @Override
    public DetalleRemito aEntidad(NuevoDetalleRemito nuevoDetalleRemito) {
        return modelMapper.map(nuevoDetalleRemito, DetalleRemito.class);
    }

    @Override
    public NuevoDetalleRemito aDTO(DetalleRemito detalleRemito) {
        return modelMapper.map(detalleRemito, NuevoDetalleRemito.class);
    }
}
