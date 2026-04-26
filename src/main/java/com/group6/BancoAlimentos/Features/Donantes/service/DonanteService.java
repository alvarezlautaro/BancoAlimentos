package com.group6.BancoAlimentos.Features.Donantes.service;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.expections.CuitDuplicadoExpections;
import com.group6.BancoAlimentos.Features.Donantes.mapper.DonanteMapper;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;
import com.group6.BancoAlimentos.Features.Donantes.repository.IDonanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonanteService implements IDonanteService{

    private final IDonanteRepository donanteRepository;


    public List<DonanteResponseDTO> findAll(){
        return donanteRepository.findAll().stream().
                map(DonanteMapper::toResponse).toList();
    }


    public DonanteResponseDTO save(DonanteRequestDTO donanteRequestDTO) throws CuitDuplicadoExpections {
        if(donanteRepository.existsByCuit(donanteRequestDTO.getCuit())){
            throw new CuitDuplicadoExpections("ERROR: CUIT REPETIDO");
        }
        Donante donante=DonanteMapper.toEntity(donanteRequestDTO);
        donanteRepository.save(donante);
        return DonanteMapper.toResponse(donante);
    }


    public Optional<DonanteResponseDTO> findByID(Long id) {

        return donanteRepository.findById(id).map(DonanteMapper::toResponse);
    }

    public DonanteResponseDTO update(Long id, DonanteRequestDTO donanteDTO){

        Donante donante=donanteRepository.findById(id).
                orElseThrow(()-> new RuntimeException("El donante no existe") );

        donante.setTelefono(donante.getTelefono());
        donante.setCuit(donanteDTO.getCuit());
        donante.setDireccion(donanteDTO.getDireccion());
        donante.setRazon_social(donanteDTO.getRazon_social());

        Donante donanteGuardado=donanteRepository.save(donante);

        return DonanteMapper.toResponse(donanteGuardado);

    }


}
