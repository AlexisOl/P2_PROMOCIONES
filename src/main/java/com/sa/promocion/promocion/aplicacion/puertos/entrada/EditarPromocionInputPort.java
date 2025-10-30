package com.sa.promocion.promocion.aplicacion.puertos.entrada;

import com.sa.promocion.promocion.aplicacion.dto.EditarPromocionDTO;
import com.sa.promocion.promocion.dominio.Promocion;

import java.util.UUID;

public interface EditarPromocionInputPort {

    Promocion editarPromocion(UUID promocionId, EditarPromocionDTO dto);
    void activarPromocion(UUID promocionId);
    void desactivarPromocion(UUID promocionId);
}
