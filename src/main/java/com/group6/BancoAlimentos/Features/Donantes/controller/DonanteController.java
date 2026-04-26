package com.group6.BancoAlimentos.Features.Donantes.controller;

import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteRequestDTO;
import com.group6.BancoAlimentos.Features.Donantes.DTO.DonanteResponseDTO;
import com.group6.BancoAlimentos.Features.Donantes.service.DonanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/donantes")
public class DonanteController {

    private final DonanteService donanteService;

    @GetMapping
    public List<DonanteResponseDTO> findAll(){

        return donanteService.findAll();
    }


    @PostMapping
    public DonanteResponseDTO save(@RequestBody DonanteRequestDTO donanteRequestDTO){

        return donanteService.save(donanteRequestDTO);
    }

    @GetMapping("/id")
    public DonanteResponseDTO findByID(@PathVariable Long id){

        return donanteService.findByID(id).
                orElseThrow(() -> new RuntimeException("No existe el donante"));
    }

    @PutMapping("/id")
    public DonanteResponseDTO update(@PathVariable Long id,@RequestBody DonanteRequestDTO donante){

        return donanteService.update(id,donante);

    }
}
