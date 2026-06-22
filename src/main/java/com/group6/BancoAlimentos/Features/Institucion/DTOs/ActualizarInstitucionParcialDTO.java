package com.group6.BancoAlimentos.Features.Institucion.DTOs;

import com.group6.BancoAlimentos.Features.Institucion.estadoPago;
import com.group6.BancoAlimentos.Features.Institucion.tipoInstitucion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la actualizacion parcial de instituciones.")
public class ActualizarInstitucionParcialDTO {
        @NotNull
        UUID externalId;

        @Schema(description = "Nombre de la institucion", example = "Escuela Nuestra Señora de Fátima")
        @Length(min = 3, message = "Debe tener al menos 3 caracteres")
        String nombre;

        @Schema(description = "Tipo de la nueva institucion", example = "COMEDOR")
        tipoInstitucion tipo;

        @Schema(description = "Direccion de la institucion", example = "Calle Falsa 123")
        String direccion;

        @Schema(description = "Telefono de contacto", example = "1234567890")
        @Pattern(regexp = "^\\d{10,11}$", message = "El telefono debe tener entre 10 y 11 digitos")
        String telefono;

        @Schema(description = "Email de la institucion", example = "contacto@example.com")
        @Email(message = "El formato del email no es valido",
                regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email;

        @Schema(description = "Estado del pago de la suscripción", example = "AL DIA")
        estadoPago estado;
}
