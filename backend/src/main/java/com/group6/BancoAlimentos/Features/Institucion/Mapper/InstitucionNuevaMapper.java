package com.group6.BancoAlimentos.Features.Institucion.Mapper;


import com.group6.BancoAlimentos.Common.mapper.IMapper;
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
    public Institucion aEntidad(NuevaInstitucionDTO dto) {
        return modelMapper.map(dto, Institucion.class);
    }

    @Override
    public NuevaInstitucionDTO aDTO(Institucion entidad) {
        return modelMapper.map(entidad, NuevaInstitucionDTO.class);
    }
}
