package com.group6.BancoAlimentos.Features.Donantes.service;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.expections.CuitDuplicadoExpections;
import com.group6.BancoAlimentos.Features.Donantes.mapper.DonanteMapper;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.Donantes.repository.IDonanteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DonanteService implements IDonanteService{

    private final IDonanteRepository donanteRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<DonanteResponseDTO> findAll(){
        return donanteRepository.findAll().stream().
                map(DonanteMapper::toResponse).toList();
    }

    @Override

    public DonanteResponseDTO save(DonanteRequestDTO donanteRequestDTO) throws CuitDuplicadoExpections {
        if(donanteRepository.existsByCuit(donanteRequestDTO.getCuit())){
            throw new CuitDuplicadoExpections("ERROR: CUIT REPETIDO");
        }
        Donante donante=DonanteMapper.toEntity(donanteRequestDTO);
        donanteRepository.save(donante);
        return DonanteMapper.toResponse(donante);
    }

    @Override

    public Optional<DonanteResponseDTO> findById(Long id) {

        return donanteRepository.findById(id).map(DonanteMapper::toResponse);
    }
    @Override

    public DonanteResponseDTO update(Long id, DonanteRequestDTO donanteDTO){

        Donante donante=donanteRepository.findById(id).
                orElseThrow(()-> new RuntimeException("El donante no existe") );

        donante.setTelefono(donante.getTelefono());
        donante.setCuit(donanteDTO.getCuit());
        donante.setDireccion(donanteDTO.getDireccion());
        donante.setRazonSocial(donanteDTO.getRazonSocial());

        Donante donanteGuardado=donanteRepository.save(donante);

        return DonanteMapper.toResponse(donanteGuardado);

    }

    @Override
    public void delete(Long id) {
        Donante donante=donanteRepository.findById(id).
                orElseThrow(()->new RuntimeException("Donante no existado"));

        donanteRepository.delete(donante);
    }

    public List<Donante> getHistorial(UUID externalId) {
        Donante donante = donanteRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RuntimeException("Donante no encontrado"));

        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisiones = reader.getRevisions(Donante.class, donante.getId());

        return revisiones.stream()
                .map(rev -> reader.find(Donante.class, donante.getId(), rev))
                .collect(Collectors.toList());
    }
}
