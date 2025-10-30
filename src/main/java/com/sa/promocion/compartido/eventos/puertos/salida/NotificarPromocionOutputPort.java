package com.sa.promocion.compartido.eventos.puertos.salida;

import com.sa.promocion.promocion.dominio.Promocion;

public interface NotificarPromocionOutputPort {

    void notificarPromocionCreada(Promocion promocion);
    void notificarPromocionActualizada(Promocion promocion);
    void notificarPromocionDesactivada(Promocion promocion);
}
