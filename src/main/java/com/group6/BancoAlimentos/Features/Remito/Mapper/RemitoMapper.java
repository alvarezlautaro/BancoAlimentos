package com.group6.BancoAlimentos.Features.Remito.Mapper;


import com.group6.BancoAlimentos.Common.mapper.IActualizarMapper;
import com.group6.BancoAlimentos.Common.mapper.IMapper;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemitoMapper implements IMapper<Remito, RemitoDTO> {
    private final ModelMapper modelMapper;

    @Override
    public RemitoDTO aDTO(Remito remito) {
        return modelMapper.map(remito, RemitoDTO.class);
    }

    @Override
    public Remito aEntidad(RemitoDTO remitoDTO) {
        return modelMapper.map(remitoDTO, Remito.class);
    }
}
