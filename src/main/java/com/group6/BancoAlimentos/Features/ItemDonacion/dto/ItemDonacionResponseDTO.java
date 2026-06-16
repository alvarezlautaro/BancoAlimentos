package com.group6.BancoAlimentos.Features.ItemDonacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDonacionResponseDTO {

        private Long id;
        private Date fechaVencimiento;
        private double valorUnitario;
        private int cantidad;
        private String productoNombre;
        private String donante;
}
