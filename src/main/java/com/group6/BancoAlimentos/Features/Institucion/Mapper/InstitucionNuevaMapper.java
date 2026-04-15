package com.group6.BancoAlimentos.Features.Institucion.Mapper;


import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstitucionNuevaMapper implements IMapper<Institucion, NuevaInstitucionDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Institucion toEntity(NuevaInstitucionDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public NuevaInstitucionDTO toDTO(Institucion entidad) {
        return modelMapper.map(entidad, NuevaInstitucionDTO.class);
    }
}
