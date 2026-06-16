package com.group6.BancoAlimentos.Features.DetalleRemito;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoRequest;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoResponse;
import com.group6.BancoAlimentos.Features.DetalleRemito.Mapper.DetalleRemitoRequestMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.Mapper.DetalleRemitoResponseMapper;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.repository.ItemDonacionRepository;
import com.group6.BancoAlimentos.Features.Remito.IRemitoRepositorio;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DetalleRemitoServicio {
    private final IDetalleRemitoRepositorio detalleRemitoRepositorio;
    private final IRemitoRepositorio remitoRepositorio;
    private final ItemDonacionRepository itemDonacionRepositorio;

    private final DetalleRemitoRequestMapper requestMapper;
    private final DetalleRemitoResponseMapper responseMapper;

    public List<DetalleRemitoResponse> encontrarTodos(){
        return detalleRemitoRepositorio.findAll()
                .stream()
                .map(responseMapper::aDTO)
                .toList();
    }

    public List<DetalleRemitoResponse> encontrarPorIdRemito(Long idRemito){
        return detalleRemitoRepositorio.findByRemitoId(idRemito)
                .stream()
                .map(responseMapper::aDTO)
                .toList();
    }

    public List<DetalleRemitoResponse> encontrarPorItemDonacion(Long idItemDonacion){
        return detalleRemitoRepositorio.findByItemDonacionId(idItemDonacion)
                .stream()
                .map(responseMapper::aDTO)
                .toList();
    }

    @Transactional
    public DetalleRemitoResponse crear(DetalleRemitoRequest dto){
        Remito remito = remitoRepositorio.findById(dto.getIdRemito())
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id: " + dto.getIdRemito()  + " no fue encontrado"));

        ItemDonacion itemDonacion = itemDonacionRepositorio.findById(dto.getIdItemDonacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("El item donacion con el id: " + dto.getIdItemDonacion() + " no fue encontrado"));

        if(detalleRemitoRepositorio.existsByRemitoIdAndItemDonacionId(dto.getIdRemito(), dto.getIdItemDonacion())){
            throw new ReglaNegocioException("El item ya existe en este remito", "DETALLE_REMITO");
        }

        if(itemDonacion.getFechaVencimiento().isBefore(remito.getFecha())){
            throw new ReglaNegocioException("No se puede agregar un item vencido al remito", "FECHA");
        }

        if(dto.getCantidad() > itemDonacion.getCantidad()){
            throw new ReglaNegocioException("La cantidad solicitada supera el stock disponible", "STOCK");
        }

        itemDonacion.setCantidad(itemDonacion.getCantidad() - dto.getCantidad());
        itemDonacionRepositorio.save(itemDonacion);

        DetalleRemito detalle = requestMapper.aEntidad(dto);
        detalle.setRemito(remito);
        detalle.setItemDonacion(itemDonacion);

        return responseMapper.aDTO(detalleRemitoRepositorio.save(detalle));
    }

    @Transactional
    public DetalleRemitoResponse actualizar(UUID externalId, DetalleRemitoRequest requestDto){
        if(!externalId.equals(requestDto.getExternalId())){
            throw new ReglaNegocioException("El id externo del path no coincide con el body", "DETALLE-REMITO");
        }

        DetalleRemito detalleExistente = detalleRemitoRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El detalle remito con id: " + externalId + " no fue encontrado"));

        ItemDonacion itemDonacion = detalleExistente.getItemDonacion();

        int disponible = itemDonacion.getCantidad() + detalleExistente.getCantidad();

        if(requestDto.getCantidad() > disponible){
            throw new ReglaNegocioException("La cantidad solicitada supera el stock disponible", "STOCK");
        }

        int diferencia = requestDto.getCantidad() - detalleExistente.getCantidad();
        itemDonacion.setCantidad(itemDonacion.getCantidad() - diferencia);
        itemDonacionRepositorio.save(itemDonacion);

        detalleExistente.setCantidad(requestDto.getCantidad());

        return responseMapper.aDTO(detalleRemitoRepositorio.save(detalleExistente));
    }

    @Transactional
    public void eliminar(UUID externalId){
        DetalleRemito detalle = detalleRemitoRepositorio.findByExternalId(externalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro el detalle con el id: " + externalId));

        ItemDonacion itemDonacion = detalle.getItemDonacion();
        itemDonacion.setCantidad(itemDonacion.getCantidad() + detalle.getCantidad());
        detalleRemitoRepositorio.delete(detalle);
    }
}