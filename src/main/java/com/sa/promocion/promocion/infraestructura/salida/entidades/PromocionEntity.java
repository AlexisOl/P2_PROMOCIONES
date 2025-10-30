package com.sa.promocion.promocion.infraestructura.salida.entidades;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promocion")
public class PromocionEntity {

    @Id
    @Column(name = "promocion_id", updatable = false, nullable = false)
    private UUID promocionId;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPromocion tipo;

    @Column(name = "porcentaje_descuento", nullable = false)
    private Double porcentajeDescuento;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activa;

    @Column(name = "cine_id", nullable = false)
    private UUID cineId;

    @Column(name = "sala_id")
    private UUID salaId;

    @Column(name = "pelicula_id")
    private UUID peliculaId;

    @Column(name = "cliente_id")
    private UUID clienteId;
}
