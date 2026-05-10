package com.group6.BancoAlimentos.Features.Institucion.Mapper;


import com.group6.BancoAlimentos.Common.mapper.IActualizarMapper;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstitucionMapper implements IActualizarMapper<Institucion, InstitucionDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Institucion aEntidad(InstitucionDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public InstitucionDTO aDTO(Institucion entidad) {
        return modelMapper.map(entidad, InstitucionDTO.class);
    }

    @Override
    public Institucion actualizarEntidad(InstitucionDTO dto, Institucion institucionExistente){
        if(dto == null || institucionExistente == null){
            return institucionExistente;
        }

        modelMapper.map(dto, institucionExistente);
        return institucionExistente;
    }
}