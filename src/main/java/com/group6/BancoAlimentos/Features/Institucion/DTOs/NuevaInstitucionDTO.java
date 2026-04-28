package com.group6.BancoAlimentos.Features.Institucion.DTOs;

import com.group6.BancoAlimentos.Features.Institucion.tipoInstitucion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la creacion de nueva institución")
public class NuevaInstitucionDTO {
        @Schema(description = "Nombre de la institucion", example = "Escuela Nuestra Señora de Fátima", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El nombre es requerido")
        @Length(min = 3, message = "Debe tener al menos 3 caracteres")
        String nombre;

        @Schema(description = "Tipo de la nueva institucion", example = "COMEDOR", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "El tipo es requerido")
        tipoInstitucion tipo;

        @Schema(description = "Direccion de la institucion", example = "Calle Falsa 123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String direccion;

        @Schema(description = "Telefono de contacto", example = "1234567890")
        String telefono;

        @Schema(description = "Email de la institucion", example = "contacto@example.com")
        @Email(message = "El formato del email no es valido",
                regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email;
        //Al ser todos de ingreso por el cliente/usuario tienen verificaciones
}
