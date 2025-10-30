package com.sa.promocion.promocion.aplicacion.puertos.salida;

import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.dominio.Promocion;

import java.util.List;
import java.util.UUID;

public interface PromocionOutputPort {

    Promocion guardarPromocion(Promocion promocion);
    Promocion obtenerPromocionPorId(UUID promocionId);
    List<Promocion> listarPromociones(FiltroPromocionDTO filtro);
    void eliminarPromocion(UUID promocionId);
}
