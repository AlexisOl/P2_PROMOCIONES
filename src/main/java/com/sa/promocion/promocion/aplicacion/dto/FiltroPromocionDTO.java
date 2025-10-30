package com.sa.promocion.promocion.aplicacion.dto;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroPromocionDTO {

    private UUID cineId;
    private UUID salaId;
    private UUID peliculaId;
    private UUID clienteId;
    private TipoPromocion tipo;
    private Boolean activa;
    private LocalDate fecha; // Para buscar promociones vigentes en una fecha específica
}
