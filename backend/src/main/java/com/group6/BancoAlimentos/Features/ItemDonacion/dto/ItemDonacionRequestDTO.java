package com.group6.BancoAlimentos.Features.ItemDonacion.dto;

import com.group6.BancoAlimentos.Features.Producto.dto.ProductoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ItemDonacionRequestDTO {

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser futura")
    private Date fechaVencimiento;

    @NotNull(message = "El valor unitario es obligatorio")
    @PositiveOrZero(message = "El valor unitario no puede ser negativo")
    private Double valorUnitario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private Long productoId;

    @Valid
    private ProductoRequestDTO producto;

    @AssertTrue(message = "Debe seleccionar un producto o ingresar uno nuevo")
    public boolean isProductoSeleccionado() {
        return productoId != null || producto != null;
    }
}
