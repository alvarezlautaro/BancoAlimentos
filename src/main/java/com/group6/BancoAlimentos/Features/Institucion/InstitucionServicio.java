package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.ActualizarInstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionNuevaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstitucionServicio {
    private final IInstitucionRepositorio institucionRepositorio;
    private final InstitucionMapper institucionDTO;
    private final ActualizarInstitucionMapper actualizarInstitucionMapper;
    private final InstitucionNuevaMapper nuevaInstitucionDTO;

    public List<InstitucionDTO> encontrarTodos(){
        return institucionRepositorio.findAll().stream()
                .map(entidad -> institucionDTO.aDTO(entidad))
                .toList();
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

    public List<InstitucionDTO> encontrarPorTipo(tipoInstitucion tipo){
        return institucionRepositorio.findByTipo(tipo).stream()
                .map(entity -> institucionDTO.aDTO(entity))
                .toList();
    }

    public List<InstitucionDTO> encontrarPorEstadoPago(estadoPago estado){
        return institucionRepositorio.findByEstado(estado).stream()
                .map(entity -> institucionDTO.aDTO(entity))
                .toList();
    }

    public InstitucionDTO guardar(NuevaInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.save(nuevaInstitucionDTO.aEntidad(dto)); //Mappeo la nueva institucion a entidad y la guardo en el repositorio

        return institucionDTO.aDTO(institucion);
    }

    public InstitucionDTO actualizar(Long id, ActualizarInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionDTO.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    public InstitucionDTO actualizacionParcial(Long id, ActualizarInstitucionDTO dto){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionDTO.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    public void eliminar(Long id){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La institucion a eliminar con el id: " + id + "no fue encontrada"));

        institucionRepositorio.delete(institucion);
    }
}
