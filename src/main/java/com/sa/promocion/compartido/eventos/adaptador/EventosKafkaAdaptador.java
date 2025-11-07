package com.sa.promocion.compartido.eventos.adaptador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.promocion.compartido.eventos.dto.PromocionEventoDTO;
import com.sa.promocion.compartido.eventos.puertos.salida.NotificarPromocionOutputPort;
import com.example.comun.DTO.promocion.*;
import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.dominio.Promocion;
import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    @Async
    protected void enviarEvento(Promocion promocion, String accion, String topic) {
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
            kafkaTemplate.send(topic, mensaje)
                    .thenAccept(result -> System.out.println(" Evento enviado a Kafka: " + topic))
                    .exceptionally(ex -> {
                        System.err.println(" Error al enviar evento Kafka: " + ex.getMessage());
                        return null;
                    });
            System.out.println("Evento enviado: " + topic + " - " + accion);
        } catch (Exception e) {
            System.err.println("Error al enviar evento de promoción: " + e.getMessage());
        }
    }
}
