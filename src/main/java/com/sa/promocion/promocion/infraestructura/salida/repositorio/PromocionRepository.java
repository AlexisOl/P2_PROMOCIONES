package com.sa.promocion.promocion.infraestructura.salida.repositorio;

import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import com.sa.promocion.promocion.infraestructura.salida.entidades.PromocionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PromocionRepository extends JpaRepository<PromocionEntity, UUID> {

    List<PromocionEntity> findByCineId(UUID cineId);
    List<PromocionEntity> findBySalaId(UUID salaId);
    List<PromocionEntity> findByPeliculaId(UUID peliculaId);
    List<PromocionEntity> findByClienteId(UUID clienteId);
    List<PromocionEntity> findByTipo(TipoPromocion tipo);
    List<PromocionEntity> findByActiva(Boolean activa);

    @Query("SELECT p FROM PromocionEntity p WHERE " +
            "(:cineId IS NULL OR p.cineId = :cineId) OR " +
            "(:salaId IS NULL OR p.salaId = :salaId) OR " +
            "(:peliculaId IS NULL OR p.peliculaId = :peliculaId) OR " +
            "(:clienteId IS NULL OR p.clienteId = :clienteId) OR " +
            "(:tipo IS NULL OR p.tipo = :tipo) OR " +
            "(:activa IS NULL OR p.activa = :activa) OR " +
            "(:fecha IS NULL OR (p.fechaInicio <= :fecha AND p.fechaFin >= :fecha))")
    List<PromocionEntity> buscarConFiltros(
            @Param("cineId") UUID cineId,
            @Param("salaId") UUID salaId,
            @Param("peliculaId") UUID peliculaId,
            @Param("clienteId") UUID clienteId,
            @Param("tipo") TipoPromocion tipo,
            @Param("activa") Boolean activa,
            @Param("fecha") LocalDate fecha
    );
}
