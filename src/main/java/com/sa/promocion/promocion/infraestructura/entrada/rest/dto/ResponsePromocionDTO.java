package com.sa.promocion.promocion.infraestructura.entrada.rest.dto;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class ResponsePromocionDTO {

    UUID promocionId;
    String nombre;
    String descripcion;
    TipoPromocion tipo;
    Double porcentajeDescuento;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    Boolean activa;
    UUID cineId;
    UUID salaId;
    UUID peliculaId;
    UUID clienteId;
}
