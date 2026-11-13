package com.group6.BancoAlimentos.Features.Remito;

import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.Institucion.IInstitucionRepositorio;
import com.group6.BancoAlimentos.Features.Institucion.Institucion;
import com.group6.BancoAlimentos.Features.Institucion.estadoPago;
import com.group6.BancoAlimentos.Features.DetalleRemito.DetalleRemito;
import com.group6.BancoAlimentos.Features.DetalleRemito.IDetalleRemitoRepositorio;
import com.group6.BancoAlimentos.Features.ItemDonacion.model.ItemDonacion;
import com.group6.BancoAlimentos.Features.ItemDonacion.repository.ItemDonacionRepository;
import com.group6.BancoAlimentos.Features.Remito.DTOs.ActualizarRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.NuevoRemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.DTOs.RemitoDTO;
import com.group6.BancoAlimentos.Features.Remito.Mapper.ActualizarRemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoMapper;
import com.group6.BancoAlimentos.Features.Remito.Mapper.RemitoNuevoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RemitoServicio {
    private final IRemitoRepositorio remitoRepositorio;
    private final IInstitucionRepositorio institucionRepositorio;
    private final ItemDonacionRepository itemDonacionRepositorio;
    private final IDetalleRemitoRepositorio detalleRemitoRepositorio;

    private final RemitoMapper remitoMapper;
    private final RemitoNuevoMapper remitoNuevoMapper;
    private final ActualizarRemitoMapper actualizarRemitoMapper;

    @Transactional(readOnly = true)
    public Page<RemitoDTO> encontrarTodos(Integer mes,
                                          Integer anio,
                                          LocalDate desde,
                                          LocalDate hasta,
                                          Pageable pageable){

        Specification<Remito> spec = Specification.allOf(
                RemitoEspecificacion.mesEquals(mes),
                RemitoEspecificacion.anioEquals(anio),
                RemitoEspecificacion.fechaDesde(desde),
                RemitoEspecificacion.fechaHasta(hasta)
        );

        return remitoRepositorio.findAll(spec, pageable)
                .map(remitoMapper::aDTO);
    }

    @Transactional(readOnly = true)
    public RemitoDTO encontrarPorID(Long id){
        return remitoMapper.aDTO(remitoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontro el remito con ese id")));
    }

    @Transactional
    public RemitoDTO crear(NuevoRemitoDTO nuevoRemitoDTO){
        if (nuevoRemitoDTO.getItems() == null || nuevoRemitoDTO.getItems().isEmpty()) {
            throw new ReglaNegocioException("El remito debe incluir al menos un item de donación", "DETALLE_REMITO");
        }

        Remito remitoNuevo = remitoNuevoMapper.aEntidad(nuevoRemitoDTO);

        Institucion institucion = institucionRepositorio.findById(nuevoRemitoDTO.getIdInstitucion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el id: " + nuevoRemitoDTO.getIdInstitucion()));

        if(institucion.getEstado() == estadoPago.DEUDOR){
            throw new ReglaNegocioException(
                    "No se puede crear un remito para una institución con deuda pendiente.", "INSTITUCION"
            );
        }

        remitoNuevo.setInstitucion(institucion);
        remitoNuevo = remitoRepositorio.save(remitoNuevo);

        Set<Long> itemsIncluidos = new HashSet<>();
        for (var itemDTO : nuevoRemitoDTO.getItems()) {
            if (!itemsIncluidos.add(itemDTO.getIdItemDonacion())) {
                throw new ReglaNegocioException("El item de donación está repetido en el remito", "DETALLE_REMITO");
            }

            ItemDonacion item = itemDonacionRepositorio.findById(itemDTO.getIdItemDonacion())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "El item de donación no existe: " + itemDTO.getIdItemDonacion()));
            if (item.getFechaVencimiento() == null || item.getFechaVencimiento().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate().isBefore(remitoNuevo.getFecha())) {
                throw new ReglaNegocioException("No se puede agregar un item vencido al remito", "FECHA");
            }
            if (itemDTO.getCantidad() > item.getCantidad()) {
                throw new ReglaNegocioException("La cantidad solicitada supera el stock disponible", "STOCK");
            }

            item.setCantidad(item.getCantidad() - itemDTO.getCantidad());
            itemDonacionRepositorio.save(item);
            DetalleRemito detalle = new DetalleRemito();
            detalle.setRemito(remitoNuevo);
            detalle.setItemDonacion(item);
            detalle.setCantidad(itemDTO.getCantidad());
            remitoNuevo.getDetalles().add(detalle);
            detalleRemitoRepositorio.save(detalle);
        }

        return remitoMapper.aDTO(remitoRepositorio.save(remitoNuevo));
    }

    @Transactional
    public RemitoDTO actualizar(Long id, ActualizarRemitoDTO remitoActualizar){
        Remito remito = remitoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id no existe."));

        Institucion nuevaInstitucion = institucionRepositorio.findById(remitoActualizar.getIdInstitucion())
                .orElseThrow(() -> new RecursoNoEncontradoException("La institución con id: " + remitoActualizar.getIdInstitucion() + " no existe."));

        remito = actualizarRemitoMapper.actualizarEntidad(remitoActualizar, remito);
        remito.setInstitucion(nuevaInstitucion);

        return remitoMapper.aDTO(remitoRepositorio.save(remito));
    }

    @Transactional
    public RemitoDTO actualizacionParcial(Long id, ActualizarRemitoDTO remitoActualizar){
        Remito remito = remitoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id no existe."));

        return remitoMapper.aDTO(remitoRepositorio.save(actualizarRemitoMapper.actualizarEntidad(remitoActualizar, remito)));
    }

    @Transactional
    public void eliminar(Long id){
        Remito remito = remitoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El remito con el id no existe"));

        remitoRepositorio.delete(remito);
    }
}
