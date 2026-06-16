package com.group6.BancoAlimentos.Features.Remito.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IActualizarMapper;
import com.group6.BancoAlimentos.Features.Remito.DTOs.ActualizarRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActualizarRemitoMapper implements IActualizarMapper<Remito, ActualizarRemitoDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Remito actualizarEntidad(ActualizarRemitoDTO DTO, Remito entidadExistente) {
        if(DTO == null || entidadExistente == null){
            return entidadExistente;
        }

        modelMapper.map(DTO, entidadExistente);

        return entidadExistente;
    }

    @Override
    public Remito aEntidad(ActualizarRemitoDTO actualizarRemitoDTO) {
        return null;
    }

    @Override
    public ActualizarRemitoDTO aDTO(Remito remito) {
        return null;
    }
}
