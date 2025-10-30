package com.sa.promocion.promocion.aplicacion.casouso;

import com.sa.promocion.compartido.eventos.puertos.salida.NotificarPromocionOutputPort;
import com.sa.promocion.promocion.aplicacion.dto.EditarPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.EditarPromocionInputPort;
import com.sa.promocion.promocion.aplicacion.puertos.salida.PromocionOutputPort;
import com.sa.promocion.promocion.dominio.Promocion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EditarPromocionCasoUso implements EditarPromocionInputPort {

    private final PromocionOutputPort promocionOutputPort;
    private final NotificarPromocionOutputPort notificarPromocionOutputPort;

    public EditarPromocionCasoUso(PromocionOutputPort promocionOutputPort,
                                  NotificarPromocionOutputPort notificarPromocionOutputPort) {
        this.promocionOutputPort = promocionOutputPort;
        this.notificarPromocionOutputPort = notificarPromocionOutputPort;
    }

    @Override
    @Transactional
    public Promocion editarPromocion(UUID promocionId, EditarPromocionDTO dto) {
        Promocion promocion = promocionOutputPort.obtenerPromocionPorId(promocionId);
        if (promocion == null) {
            throw new IllegalArgumentException("La promoción no existe");
        }

        if (dto.getNombre() != null) {
            promocion.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            promocion.setDescripcion(dto.getDescripcion());
        }
        if (dto.getTipo() != null) {
            promocion.setTipo(dto.getTipo());
        }
        if (dto.getPorcentajeDescuento() != null) {
            promocion.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        }
        if (dto.getFechaInicio() != null) {
            promocion.setFechaInicio(dto.getFechaInicio());
        }
        if (dto.getFechaFin() != null) {
            promocion.setFechaFin(dto.getFechaFin());
        }
        if (dto.getActiva() != null) {
            promocion.setActiva(dto.getActiva());
        }
        if (dto.getSalaId() != null) {
            promocion.setSalaId(dto.getSalaId());
        }
        if (dto.getPeliculaId() != null) {
            promocion.setPeliculaId(dto.getPeliculaId());
        }
        if (dto.getClienteId() != null) {
            promocion.setClienteId(dto.getClienteId());
        }

        Promocion promocionActualizada = promocionOutputPort.guardarPromocion(promocion);

        // Notificar actualización
        notificarPromocionOutputPort.notificarPromocionActualizada(promocionActualizada);

        return promocionActualizada;
    }

    @Override
    @Transactional
    public void activarPromocion(UUID promocionId) {
        Promocion promocion = promocionOutputPort.obtenerPromocionPorId(promocionId);
        if (promocion == null) {
            throw new IllegalArgumentException("La promoción no existe");
        }
        promocion.activar();
        Promocion promocionActualizada = promocionOutputPort.guardarPromocion(promocion);
        notificarPromocionOutputPort.notificarPromocionActualizada(promocionActualizada);
    }

    @Override
    @Transactional
    public void desactivarPromocion(UUID promocionId) {
        Promocion promocion = promocionOutputPort.obtenerPromocionPorId(promocionId);
        if (promocion == null) {
            throw new IllegalArgumentException("La promoción no existe");
        }
        promocion.desactivar();
        Promocion promocionActualizada = promocionOutputPort.guardarPromocion(promocion);
        notificarPromocionOutputPort.notificarPromocionDesactivada(promocionActualizada);
    }
}
