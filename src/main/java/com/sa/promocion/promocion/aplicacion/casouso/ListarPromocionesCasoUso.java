package com.sa.promocion.promocion.aplicacion.casouso;

import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.ListarPromocionesInputPort;
import com.sa.promocion.promocion.aplicacion.puertos.salida.PromocionOutputPort;
import com.sa.promocion.promocion.dominio.Promocion;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ListarPromocionesCasoUso implements ListarPromocionesInputPort {

    private final PromocionOutputPort promocionOutputPort;

    public ListarPromocionesCasoUso(PromocionOutputPort promocionOutputPort) {
        this.promocionOutputPort = promocionOutputPort;
    }

    @Override
    public List<Promocion> listarPromociones(FiltroPromocionDTO filtro) {
        return promocionOutputPort.listarPromociones(filtro);
    }

    @Override
    public Promocion obtenerPromocionPorId(UUID promocionId) {
        Promocion promocion = promocionOutputPort.obtenerPromocionPorId(promocionId);
        if (promocion == null) {
            throw new IllegalArgumentException("La promoción no existe");
        }
        return promocion;
    }

    @Override
    public Promocion obtenerMejorPromocion(FiltroPromocionDTO filtro) {
        List<Promocion> promociones = promocionOutputPort.listarPromociones(filtro);

        // Filtrar solo promociones vigentes y activas
        return promociones.stream()
                .filter(Promocion::estaVigente)
                .max(Comparator.comparing(Promocion::getPorcentajeDescuento))
                .orElse(null);
    }
}
