package com.group6.BancoAlimentos.Features.Institucion.Mapper;

import com.group6.BancoAlimentos.Common.mapper.IActualizarMapper;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionParcialDTO;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActualizarInstitucionMapper implements IActualizarMapper<Institucion, ActualizarInstitucionParcialDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Institucion aEntidad(ActualizarInstitucionParcialDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public ActualizarInstitucionParcialDTO aDTO(Institucion entidad) {
        return modelMapper.map(entidad, ActualizarInstitucionParcialDTO.class);
    }

    @Override
    public Institucion actualizarEntidad(ActualizarInstitucionParcialDTO dto, Institucion institucionExistente){
        if(dto == null || institucionExistente == null){
            return institucionExistente;
        }

        modelMapper.map(dto, institucionExistente);
        return institucionExistente;
    }
}
