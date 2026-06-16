package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionDTO;
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

@Service
@Transactional
@RequiredArgsConstructor
public class InstitucionServicio {
    private final IInstitucionRepositorio institucionRepositorio;
    private final InstitucionMapper institucionDTO;
    private final ActualizarInstitucionMapper actualizarInstitucionMapper;
    private final InstitucionNuevaMapper nuevaInstitucionDTO;

    public Page<InstitucionDTO> encontrarTodos(Pageable pageable){
        return institucionRepositorio.findAll(pageable)
                .map(institucionDTO::aDTO);
    }

    public InstitucionDTO encontrarPorID(Long id){
        return institucionRepositorio.findById(id)
                .map(entidad -> institucionDTO.aDTO(entidad))
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el id: " + id));
    }

    public InstitucionDTO encontrarPorNombre(String nombre){
        return institucionRepositorio.findByNombre(nombre)
                .map(entidad -> institucionDTO.aDTO(entidad))
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el nombre: " + nombre));
    }

    public Page<InstitucionDTO> encontrarPorTipo(tipoInstitucion tipo, Pageable pageable){
        return institucionRepositorio.findByTipo(tipo, pageable)
                .map(institucionDTO::aDTO);
    }

    public Page<InstitucionDTO> encontrarPorEstadoPago(estadoPago estado, Pageable pageable){
        return institucionRepositorio.findByEstado(estado, pageable)
                .map(institucionDTO::aDTO);
    }

    @Transactional
    public InstitucionDTO guardar(NuevaInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.save(nuevaInstitucionDTO.aEntidad(dto));
        return institucionDTO.aDTO(institucion);
    }

    @Transactional
    public InstitucionDTO actualizar(Long id, ActualizarInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionDTO.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    @Transactional
    public InstitucionDTO actualizacionParcial(Long id, ActualizarInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionDTO.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    @Transactional
    public void eliminar(Long id){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La institucion a eliminar con el id: " + id + " no fue encontrada"));

        institucionRepositorio.delete(institucion);
    }
}
