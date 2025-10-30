package com.sa.promocion.compartido.eventos.adaptador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.promocion.compartido.eventos.dto.PromocionEventoDTO;
import com.sa.promocion.compartido.eventos.puertos.salida.NotificarPromocionOutputPort;

import com.sa.promocion.promocion.dominio.Promocion;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventosKafkaAdaptador implements NotificarPromocionOutputPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EventosKafkaAdaptador(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void notificarPromocionCreada(Promocion promocion) {
        enviarEvento(promocion, "CREADA", "promocion-creada");
    }

    @Override
    public void notificarPromocionActualizada(Promocion promocion) {
        enviarEvento(promocion, "ACTUALIZADA", "promocion-actualizada");
    }

    @Override
    public void notificarPromocionDesactivada(Promocion promocion) {
        enviarEvento(promocion, "DESACTIVADA", "promocion-desactivada");
    }

    private void enviarEvento(Promocion promocion, String accion, String topic) {
        try {
            PromocionEventoDTO evento = new PromocionEventoDTO(
                    promocion.getPromocionId(),
                    promocion.getNombre(),
                    promocion.getTipo(),
                    promocion.getPorcentajeDescuento(),
                    promocion.getFechaInicio(),
                    promocion.getFechaFin(),
                    promocion.getActiva(),
                    promocion.getCineId(),
                    promocion.getSalaId(),
                    promocion.getPeliculaId(),
                    promocion.getClienteId(),
                    accion
            );

            String mensaje = objectMapper.writeValueAsString(evento);
            kafkaTemplate.send(topic, mensaje);
            System.out.println("Evento enviado: " + topic + " - " + accion);
        } catch (Exception e) {
            System.err.println("Error al enviar evento de promoción: " + e.getMessage());
        }
    }
}
