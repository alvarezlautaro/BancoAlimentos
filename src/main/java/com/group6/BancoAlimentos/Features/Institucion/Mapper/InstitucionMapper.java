package com.group6.BancoAlimentos.Features.Institucion.Mapper;


import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstitucionMapper implements IMapper<Institucion, InstitucionDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Institucion toEntity(InstitucionDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public InstitucionDTO toDTO(Institucion entidad) {
        return modelMapper.map(entidad, InstitucionDTO.class);
    }
}