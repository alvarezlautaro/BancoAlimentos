package com.group6.BancoAlimentos.Features.DetalleRemito.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoResponse;
import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleRemitoResponseMapper implements IMapper<DetalleRemito, DetalleRemitoResponse> {
    private final ModelMapper modelMapper;

    @Override
    public DetalleRemitoResponse aDTO(DetalleRemito detalleRemito) {
        return modelMapper.map(detalleRemito, DetalleRemitoResponse.class);
    }

    @Override
    public DetalleRemito aEntidad(DetalleRemitoResponse detalleRemitoResponse) {
        return modelMapper.map(detalleRemitoResponse, DetalleRemito.class);
    }
}
