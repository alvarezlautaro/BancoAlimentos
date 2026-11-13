package com.group6.BancoAlimentos.Features.Institucion;

import com.group6.BancoAlimentos.Common.dto.RevisionDTO;
import com.group6.BancoAlimentos.Common.exception.RecursoNoEncontradoException;
import com.group6.BancoAlimentos.Common.exception.ReglaNegocioException;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.ActualizarInstitucionParcialDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.InstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.DTOs.NuevaInstitucionDTO;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.ActualizarInstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionMapper;
import com.group6.BancoAlimentos.Features.Institucion.Mapper.InstitucionNuevaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.envers.RevisionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitucionServicio {
    private final IInstitucionRepositorio institucionRepositorio;
    private final InstitucionMapper institucionMapper;
    private final ActualizarInstitucionMapper actualizarInstitucionMapper;
    private final InstitucionNuevaMapper nuevaInstitucionDTO;
    @PersistenceContext
    private EntityManager entityManager;

    public Page<InstitucionDTO> encontrarTodos(Pageable pageable){
        return institucionRepositorio.findAll(pageable)
                .map(institucionMapper::aDTO);
    }

    public InstitucionDTO encontrarPorID(Long id){
        return institucionRepositorio.findById(id)
                .map(entidad -> institucionMapper.aDTO(entidad))
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada con el id: " + id));
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
    public InstitucionDTO actualizar(Long id, InstitucionDTO dto){
        if(!id.equals(dto.getId())){
            throw new ReglaNegocioException("El id del path no coincide con el body", "DETALLE-REMITO");
        }

        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        institucion.setNombre(dto.getNombre());
        institucion.setTipo(dto.getTipo());
        institucion.setDireccion(dto.getDireccion());
        institucion.setTelefono(dto.getTelefono());
        institucion.setEmail(dto.getEmail());
        institucion.setEstado(dto.getEstado());

        return institucionMapper.aDTO(
                institucionRepositorio.save(institucion)
        );
    }

    @Transactional
    public InstitucionDTO actualizacionParcial(Long id, ActualizarInstitucionParcialDTO dto){
        if(!id.equals(dto.getId())){
            throw new ReglaNegocioException("El id del path no coincide con el body", "DETALLE-REMITO");
        }

        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institucion no encontrada"));

        return institucionMapper.aDTO(
                institucionRepositorio.save(actualizarInstitucionMapper.actualizarEntidad(dto, institucion))
        );
    }

    @Transactional
    public void eliminar(Long id){
        Institucion institucion = institucionRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La institucion a eliminar con el id no fue encontrada"));

        institucionRepositorio.delete(institucion);
    }

    @Transactional(readOnly = true)
public List<RevisionDTO<InstitucionDTO>> getHistorial(Long id) {
    if (!institucionRepositorio.existsById(id)) {
        throw new RecursoNoEncontradoException("Institucion no encontrada con el id: " + id);
    }

    AuditReader reader = AuditReaderFactory.get(entityManager);

    List<Object[]> filas = reader.createQuery()
            .forRevisionsOfEntity(Institucion.class, false, true)
            .add(AuditEntity.id().eq(id))
            .addOrder(AuditEntity.revisionNumber().desc())
            .getResultList();

    return filas.stream().map(fila -> {
        Institucion institucion = (Institucion) fila[0];
        DefaultRevisionEntity rev = (DefaultRevisionEntity) fila[1];
        RevisionType tipo = (RevisionType) fila[2];

        return new RevisionDTO<>(
                Long.valueOf(rev.getId()),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(rev.getTimestamp()), ZoneId.systemDefault()),
                tipo.name(),
                institucionMapper.aDTO(institucion)
        );
    }).toList();
}
}
