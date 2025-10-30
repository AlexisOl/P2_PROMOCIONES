package com.sa.promocion.compartido.eventos.dto;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionEventoDTO {

    private UUID promocionId;
    private String nombre;
    private TipoPromocion tipo;
    private Double porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;
    private UUID cineId;
    private UUID salaId;
    private UUID peliculaId;
    private UUID clienteId;
    private String accion; // CREADA, ACTUALIZADA, DESACTIVADA
}
