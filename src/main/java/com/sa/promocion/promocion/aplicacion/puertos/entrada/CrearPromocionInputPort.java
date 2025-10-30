package com.sa.promocion.promocion.aplicacion.puertos.entrada;

import com.sa.promocion.promocion.aplicacion.dto.CrearPromocionDTO;
import com.sa.promocion.promocion.dominio.Promocion;

public interface CrearPromocionInputPort {

    Promocion crearPromocion(CrearPromocionDTO dto);
}
