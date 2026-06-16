package com.group6.BancoAlimentos.Features.Remito.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IActualizarMapper;
import com.group6.BancoAlimentos.Common.mapper.IMapper;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemitoNuevoMapper implements IMapper<Remito, NuevoRemitoDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Remito aEntidad(NuevoRemitoDTO remitoNuevo) {
        return modelMapper.map(remitoNuevo, Remito.class);
    }

    @Override
    public NuevoRemitoDTO aDTO(Remito remito) {
        return modelMapper.map(remito, NuevoRemitoDTO.class);
    }
}
