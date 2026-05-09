package com.group6.BancoAlimentos.Features.Institucion.Mapper;

import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActualizarInstitucionMapper implements IActualizarMapper<Institucion, ActualizarInstitucionDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Institucion aEntidad(ActualizarInstitucionDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public ActualizarInstitucionDTO aDTO(Institucion entidad) {
        return modelMapper.map(entidad, ActualizarInstitucionDTO.class);
    }

    @Override
    public Institucion actualizarEntidad(ActualizarInstitucionDTO dto, Institucion institucionExistente){
        if(dto == null || institucionExistente == null){
            return institucionExistente;
        }

        modelMapper.map(dto, institucionExistente);
        return institucionExistente;
    }
}
