package com.group6.BancoAlimentos.Features.Donantes.mapper;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.model.Donante;

public class DonanteMapper {
    public static DonanteResponseDTO toResponse(Donante donante){

        DonanteResponseDTO donanteResponseDTO= new DonanteResponseDTO();
        donanteResponseDTO.setRazonSocial(donante.getRazonSocial());
        donanteResponseDTO.setCuit(donante.getCuit());
        donanteResponseDTO.setDireccion(donante.getDireccion());
        donanteResponseDTO.setEmail(donante.getEmail());
        donanteResponseDTO.setTelefono(donante.getTelefono());
        return donanteResponseDTO;

    }

    public static Donante toEntity(DonanteRequestDTO donanteRequestDTO){

        Donante donante1=new Donante();
        donante1.setRazonSocial(donanteRequestDTO.getRazonSocial());
        donante1.setCuit(donanteRequestDTO.getCuit());
        donante1.setDireccion(donanteRequestDTO.getDireccion());
        donante1.setEmail(donanteRequestDTO.getEmail());
        donante1.setTelefono(donanteRequestDTO.getTelefono());
        return donante1;
    }
}