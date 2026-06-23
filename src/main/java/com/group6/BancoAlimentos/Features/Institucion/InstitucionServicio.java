package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionParcialDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.ActualizarInstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionNuevaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstitucionServicio {
    private final IInstitucionRepositorio institucionRepositorio;
    private final InstitucionMapper institucionMapper;
    private final ActualizarInstitucionMapper actualizarInstitucionMapper;
    private final InstitucionNuevaMapper nuevaInstitucionDTO;

    public Page<InstitucionDTO> encontrarTodos(Pageable pageable){
        return institucionRepositorio.findAll(pageable)
                .map(institucionMapper::aDTO);
    }

    public InstitucionDTO encontrarPorID(UUID externalId){
        return institucionRepositorio.findByExternalId(externalId)
                .map(entidad -> institucionMapper.aDTO(entidad))
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el id externo: " + externalId));
    }

    public InstitucionDTO encontrarPorNombre(String nombre){
        return institucionRepositorio.findByNombre(nombre)
                .map(entidad -> institucionMapper.aDTO(entidad))
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el nombre: " + nombre));
    }

    public Page<InstitucionDTO> encontrarPorTipo(tipoInstitucion tipo, Pageable pageable){
        return institucionRepositorio.findByTipo(tipo, pageable)
                .map(institucionMapper::aDTO);
    }

    public Page<InstitucionDTO> encontrarPorEstadoPago(estadoPago estado, Pageable pageable){
        return institucionRepositorio.findByEstado(estado, pageable)
                .map(institucionMapper::aDTO);
    }

    @Transactional
    public InstitucionDTO guardar(NuevaInstitucionDTO dto){
        Institucion institucion = nuevaInstitucionDTO.aEntidad(dto);
        Institucion institucionGuardada = institucionRepositorio.save(institucion);
        return institucionMapper.aDTO(institucionGuardada);
    }

    @Transactional
    public InstitucionDTO actualizar(UUID externalId, InstitucionDTO dto){
        if(!externalId.equals(dto.getExternalId())){
            throw new ReglaNegocioException("El id externo del path no coincide con el body", "DETALLE-REMITO");
        }

        Institucion institucion = institucionRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        institucion.setNombre(dto.getNombre());
        institucion.setTipo(dto.getTipo());
        institucion.setTelefono(dto.getTelefono());
        institucion.setEmail(dto.getEmail());
        institucion.setEstado(dto.getEstado());

        return institucionMapper.aDTO(
                institucionRepositorio.save(institucion)
        );
    }

    @Transactional
    public InstitucionDTO actualizacionParcial(UUID externalId, ActualizarInstitucionParcialDTO dto){
        if(!externalId.equals(dto.getExternalId())){
            throw new ReglaNegocioException("El id externo del path no coincide con el body", "DETALLE-REMITO");
        }

        Institucion institucion = institucionRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionMapper.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    @Transactional
    public void eliminar(UUID externalId){
        Institucion institucion = institucionRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La institucion a eliminar con el id externo: " + externalId + " no fue encontrada"));

        institucionRepositorio.delete(institucion);
    }
}
