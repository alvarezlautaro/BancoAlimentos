package com.group6.BancoAlimentos.Features.DetalleRemito;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoRequest;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.DetalleRemitoResponse;
import com.group6.BancoAlimentos.Features.DetalleRemito.DTOs.NuevoDetalleRemito;
import com.group6.BancoAlimentos.Features.DetalleRemito.Mapper.DetalleRemitoRequestMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.Mapper.DetalleRemitoResponseMapper;
import com.group6.BancoAlimentos.Features.DetalleRemito.Mapper.NuevoDetalleRemitoMapper;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.repository.ItemDonacionRepository;
import com.group6.BancoAlimentos.Features.Remito.IRemitoRepositorio;
import com.group6.BancoAlimentos.Features.Remito.Remito;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetalleRemitoServicio {
    private final IDetalleRemitoRepositorio detalleRemitoRepositorio;
    private final IRemitoRepositorio remitoRepositorio;
    private final ItemDonacionRepository itemDonacionRepositorio;

    private final DetalleRemitoResponseMapper responseMapper;
    private final NuevoDetalleRemitoMapper nuevoMapper;

    public Page<DetalleRemitoResponse> encontrarTodos(Pageable pageable){
        return detalleRemitoRepositorio.findAll(pageable)
                .map(responseMapper::aDTO);
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
    public List<DetalleRemitoResponse> crear(List<NuevoDetalleRemito> listaDetalleRemitos){
        if(listaDetalleRemitos.isEmpty()){
            throw new IllegalArgumentException("La lista de detalles remito no puede estar vacia");
        }

        Long idRemito = listaDetalleRemitos.getFirst().getIdRemito();
        Remito remito = remitoRepositorio.findById(idRemito)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con id: " + idRemito + " no existe"));

        List<DetalleRemitoResponse> listaRespuestas = new ArrayList<>();

        for(NuevoDetalleRemito dto : listaDetalleRemitos){

            if (!dto.getIdRemito().equals(idRemito)) {
                throw new ReglaNegocioException("Todos los detalles deben pertenecer al mismo remito", "REMITO");
            }

            ItemDonacion itemDonacion = itemDonacionRepositorio.findById(dto.getIdItemDonacion())
                    .orElseThrow(() -> new RecursoNoEncontradoException("El item donacion con el id: " + dto.getIdItemDonacion() + " no fue encontrado"));

            if(detalleRemitoRepositorio.existsByRemitoIdAndItemDonacionId(dto.getIdRemito(), dto.getIdItemDonacion())){
                throw new ReglaNegocioException("El item ya existe en este remito", "DETALLE_REMITO");
            }

            LocalDate fechaVencimiento = itemDonacion.getFechaVencimiento()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if(fechaVencimiento.isBefore(remito.getFecha())){
                throw new ReglaNegocioException("No se puede agregar un item vencido al remito", "FECHA");
            }

            if(dto.getCantidad() > itemDonacion.getCantidad()){
                throw new ReglaNegocioException("La cantidad solicitada supera el stock disponible", "STOCK");
            }

            itemDonacion.setCantidad(itemDonacion.getCantidad() - dto.getCantidad());
            itemDonacionRepositorio.save(itemDonacion);

            DetalleRemito detalle = nuevoMapper.aEntidad(dto);
            detalle.setRemito(remito);
            detalle.setItemDonacion(itemDonacion);

            DetalleRemito detalleGuardado = detalleRemitoRepositorio.save(detalle);

            listaRespuestas.add(responseMapper.aDTO(detalleGuardado));
        }

        return listaRespuestas;
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