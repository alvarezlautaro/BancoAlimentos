package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Common.exception.InstitucionNoEncontradaException;
import com.group6.BancoAlimentos.Common.exception.RemitoNoEncontradoException;
import com.group6.BancoAlimentos.Features.Institucion.IInstitucionRepositorio;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.ActualizarInstitucionMapper;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoNuevoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemitoServicio {
    private final IRemitoRepositorio remitoRepositorio;
    private final IInstitucionRepositorio institucionRepositorio;

    private final RemitoMapper remitoMapper;
    private final RemitoNuevoMapper actualizarRemitoMapper;

    public List<RemitoDTO> encontrarTodos(Integer mes,
                                          Integer anio,
                                          LocalDate desde,
                                          LocalDate hasta){

        Specification<Remito> spec = Specification.allOf(
                RemitoEspecificacion.mesEquals(mes),
                RemitoEspecificacion.anioEquals(anio),
                RemitoEspecificacion.fechaDesde(desde),
                RemitoEspecificacion.fechaHasta(hasta)
        );

        return remitoRepositorio.findAll(spec).stream()
                .map(remito -> remitoMapper.aDTO(remito))
                .toList();
    }

    public RemitoDTO encontrarPorExternalID(UUID uuid){
        return remitoMapper.aDTO(remitoRepositorio.findByExternalId(uuid)
                .orElseThrow(() -> new RemitoNoEncontradoException("No se encontro el remito con el id externo: "+ uuid)));
    }

    public RemitoDTO guardar(NuevoRemitoDTO nuevoRemitoDTO){
        Remito remitoNuevo = actualizarRemitoMapper.aEntidad(nuevoRemitoDTO);

        Institucion institucion = institucionRepositorio.findById(nuevoRemitoDTO.getIdInstitucion())
                .orElseThrow(() -> new InstitucionNoEncontradaException("Institucion no encontrada con el id: " + nuevoRemitoDTO.getIdInstitucion()));

        remitoNuevo.setInstitucion(institucion);

        return remitoMapper.aDTO(remitoRepositorio.save(remitoNuevo));
    }
}
