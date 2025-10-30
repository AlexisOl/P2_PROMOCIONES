package com.sa.promocion.promocion.aplicacion.dto;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPromocionDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotNull(message = "El tipo de promoción es obligatorio")
    private TipoPromocion tipo;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El porcentaje mínimo es 1")
    @Max(value = 100, message = "El porcentaje máximo es 100")
    private Double porcentajeDescuento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El ID del cine es obligatorio")
    private UUID cineId;

    // IDs opcionales según el tipo
    private UUID salaId;
    private UUID peliculaId;
    private UUID clienteId;
}
