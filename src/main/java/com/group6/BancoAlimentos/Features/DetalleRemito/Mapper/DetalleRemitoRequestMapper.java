package com.group6.BancoAlimentos.Features.DetalleRemito.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoRequest;
import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleRemitoRequestMapper implements IMapper<DetalleRemito, DetalleRemitoRequest> {
    private final ModelMapper modelMapper;

    @Override
    public DetalleRemitoRequest aDTO(DetalleRemito detalleRemito) {
        return modelMapper.map(detalleRemito, DetalleRemitoRequest.class);
    }

    @Override
    public DetalleRemito aEntidad(DetalleRemitoRequest detalleRemitoRequest) {
        return modelMapper.map(detalleRemitoRequest, DetalleRemito.class);
    }
}
