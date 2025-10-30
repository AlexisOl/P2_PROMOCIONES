package com.sa.promocion.promocion.dominio;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Promocion {

    private UUID promocionId;
    private String nombre;
    private String descripcion;
    private TipoPromocion tipo;
    private Double porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;

    // IDs opcionales según el tipo de promoción
    private UUID cineId;         // ID del cine dueño de la promoción
    private UUID salaId;         // Si es promoción de SALA
    private UUID peliculaId;     // Si es promoción de PELICULA
    private UUID clienteId;      // Si es promoción de CLIENTE

    public Promocion(UUID promocionId, String nombre, String descripcion,
                     TipoPromocion tipo, Double porcentajeDescuento,
                     LocalDate fechaInicio, LocalDate fechaFin, Boolean activa,
                     UUID cineId, UUID salaId, UUID peliculaId, UUID clienteId) {
        this.promocionId = promocionId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.cineId = cineId;
        this.salaId = salaId;
        this.peliculaId = peliculaId;
        this.clienteId = clienteId;
        validarPromocion();
    }

    private void validarPromocion() {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la promoción es obligatorio");
        }
        if (porcentajeDescuento == null || porcentajeDescuento <= 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 1 y 100");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        if (cineId == null) {
            throw new IllegalArgumentException("El ID del cine es obligatorio");
        }
    }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }

    public boolean aplicaA(TipoPromocion tipoConsulta) {
        return this.tipo == tipoConsulta || this.tipo == TipoPromocion.AMBOS;
    }
}
