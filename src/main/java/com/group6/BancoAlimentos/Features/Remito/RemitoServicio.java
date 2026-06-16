package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.Institucion.IInstitucionRepositorio;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import com.group6.BancoAlimentos.Features.Institucion.estadoPago;
import com.group6.BancoAlimentos.Features.Remito.DTOs.ActualizarRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Mapper.ActualizarRemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoNuevoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RemitoServicio {
    private final IRemitoRepositorio remitoRepositorio;
    private final IInstitucionRepositorio institucionRepositorio;

    private final RemitoMapper remitoMapper;
    private final RemitoNuevoMapper remitoNuevoMapper;
    private final ActualizarRemitoMapper actualizarRemitoMapper;

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
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro el remito con el id externo: "+ uuid)));
    }

    @Transactional
    public RemitoDTO crear(NuevoRemitoDTO nuevoRemitoDTO){
        Remito remitoNuevo = remitoNuevoMapper.aEntidad(nuevoRemitoDTO);

        Institucion institucion = institucionRepositorio.findById(nuevoRemitoDTO.getIdInstitucion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el id: " + nuevoRemitoDTO.getIdInstitucion()));

        if(institucion.getEstado() == estadoPago.DEUDOR){
            throw new ReglaNegocioException(
                    "No se puede crear un remito para una institución con deuda pendiente.", "INSTITUCION"
            );
        }

        remitoNuevo.setInstitucion(institucion);

        return remitoMapper.aDTO(remitoRepositorio.save(remitoNuevo));
    }

    @Transactional
    public RemitoDTO actualizar(UUID externalId, ActualizarRemitoDTO remitoActualizar){
        Remito remito = remitoRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id externo: " + externalId + " no existe."));

        return remitoMapper.aDTO(remitoRepositorio.save(actualizarRemitoMapper.actualizarEntidad(remitoActualizar, remito)));
    }

    @Transactional
    public RemitoDTO actualizacionParcial(UUID externalId, ActualizarRemitoDTO remitoActualizar){
        Remito remito = remitoRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id externo: " + externalId + " no existe."));

        return remitoMapper.aDTO(remitoRepositorio.save(actualizarRemitoMapper.actualizarEntidad(remitoActualizar, remito)));
    }

    @Transactional
    public void eliminar(UUID externalId){
        Remito remito = remitoRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id externo: " + externalId + " no existe"));

        remitoRepositorio.delete(remito);
    }
}
