package com.sa.promocion.promocion.aplicacion.dto;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarPromocionDTO {

    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    private TipoPromocion tipo;

    @Min(value = 1, message = "El porcentaje mínimo es 1")
    @Max(value = 100, message = "El porcentaje máximo es 100")
    private Double porcentajeDescuento;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean activa;

    private UUID salaId;
    private UUID peliculaId;
    private UUID clienteId;
}
