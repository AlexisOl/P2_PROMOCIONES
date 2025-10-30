package com.sa.promocion.promocion.infraestructura.salida.adaptador;

import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.salida.PromocionOutputPort;
import com.sa.promocion.promocion.dominio.Promocion;
import com.sa.promocion.promocion.infraestructura.salida.mapper.PromocionMapper;
import com.sa.promocion.promocion.infraestructura.salida.repositorio.PromocionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PromocionPersistenciaAdaptador implements PromocionOutputPort {

    private final PromocionRepository repository;
    private final PromocionMapper mapper;

    @Override
    @Transactional
    public Promocion guardarPromocion(Promocion promocion) {
        return mapper.toDomain(
                repository.save(mapper.toEntity(promocion))
        );
    }

    @Override
    public Promocion obtenerPromocionPorId(UUID promocionId) {
        return repository.findById(promocionId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Promocion> listarPromociones(FiltroPromocionDTO filtro) {
        return mapper.toDomainList(
                repository.buscarConFiltros(
                        filtro.getCineId(),
                        filtro.getSalaId(),
                        filtro.getPeliculaId(),
                        filtro.getClienteId(),
                        filtro.getTipo(),
                        filtro.getActiva(),
                        filtro.getFecha()
                )
        );
    }

    @Override
    @Transactional
    public void eliminarPromocion(UUID promocionId) {
        repository.deleteById(promocionId);
    }
}
