package com.sa.promocion.promocion.aplicacion.casouso;

import com.sa.promocion.compartido.eventos.puertos.salida.NotificarPromocionOutputPort;
import com.sa.promocion.promocion.aplicacion.dto.CrearPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.CrearPromocionInputPort;
import com.sa.promocion.promocion.aplicacion.puertos.salida.PromocionOutputPort;
import com.sa.promocion.promocion.dominio.Promocion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CrearPromocionCasoUso implements CrearPromocionInputPort {

    private final PromocionOutputPort promocionOutputPort;
    private final NotificarPromocionOutputPort notificarPromocionOutputPort;

    public CrearPromocionCasoUso(PromocionOutputPort promocionOutputPort,
                                 NotificarPromocionOutputPort notificarPromocionOutputPort) {
        this.promocionOutputPort = promocionOutputPort;
        this.notificarPromocionOutputPort = notificarPromocionOutputPort;
    }

    @Override
    @Transactional
    public Promocion crearPromocion(CrearPromocionDTO dto) {
        Promocion promocion = new Promocion(
                UUID.randomUUID(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getTipo(),
                dto.getPorcentajeDescuento(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                true, // Activa por defecto
                dto.getCineId(),
                dto.getSalaId(),
                dto.getPeliculaId(),
                dto.getClienteId()
        );

        Promocion promocionGuardada = promocionOutputPort.guardarPromocion(promocion);

        // Notificar que se creó una promoción
        notificarPromocionOutputPort.notificarPromocionCreada(promocionGuardada);

        return promocionGuardada;
    }
}
