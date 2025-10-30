package com.sa.promocion.promocion.aplicacion.puertos.entrada;

import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.dominio.Promocion;

import java.util.List;
import java.util.UUID;

public interface ListarPromocionesInputPort {

    List<Promocion> listarPromociones(FiltroPromocionDTO filtro);
    Promocion obtenerPromocionPorId(UUID promocionId);
    Promocion obtenerMejorPromocion(FiltroPromocionDTO filtro);
}
