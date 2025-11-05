package com.sa.promocion.compartido.eventos.adaptador;

import com.example.comun.DTO.promocion.ConsultaPromocionDTO;
import com.example.comun.DTO.promocion.PromocionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.promocion.promocion.aplicacion.dto.FiltroPromocionDTO;
import com.sa.promocion.promocion.aplicacion.puertos.entrada.ListarPromocionesInputPort;
import com.sa.promocion.promocion.dominio.Promocion;
import com.sa.promocion.promocion.dominio.objetovalor.TipoPromocion;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConsultaPromocionListener {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ListarPromocionesInputPort listarPromocionesInputPort;

    public ConsultaPromocionListener(KafkaTemplate<String, String> kafkaTemplate,
                                     ObjectMapper objectMapper,
                                     ListarPromocionesInputPort listarPromocionesInputPort) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.listarPromocionesInputPort = listarPromocionesInputPort;
    }

    @KafkaListener(topics = "consulta-promocion", groupId = "promociones-group")
    public void handleConsultaPromocion(@Payload String mensaje,
                                        @Header(value = KafkaHeaders.CORRELATION_ID, required = false) String correlationId) {
        try {
            if (correlationId == null) {
                System.err.println("Missing correlationId in consulta-promocion");
                return;
            }

            // Deserializar la consulta
            ConsultaPromocionDTO consulta = objectMapper.readValue(mensaje, ConsultaPromocionDTO.class);

            System.out.println("Procesando consulta de promoción - CorrelationId: " + correlationId);
            System.out.println("Filtros: cineId=" + consulta.getCineId() +
                    ", tipo=" + consulta.getTipo());

            // Construir filtro para buscar la mejor promoción
            FiltroPromocionDTO filtro = new FiltroPromocionDTO();
            filtro.setCineId(consulta.getCineId());
            filtro.setSalaId(consulta.getSalaId());
            filtro.setPeliculaId(consulta.getPeliculaId());
            filtro.setClienteId(consulta.getClienteId());

            // Convertir tipo String a TipoPromocion enum
            if (consulta.getTipo() != null) {
                try {
                    filtro.setTipo(TipoPromocion.valueOf(consulta.getTipo()));
                } catch (IllegalArgumentException e) {
                    System.err.println("Tipo de promoción inválido: " + consulta.getTipo());
                }
            }

            filtro.setActiva(true); // Solo promociones activas
            filtro.setFecha(LocalDate.now()); // Solo vigentes hoy

            // Buscar mejor promoción
            Promocion mejorPromocion = listarPromocionesInputPort.obtenerMejorPromocion(filtro);

            // Preparar respuesta usando PromocionDTO
            PromocionDTO respuesta;
            if (mejorPromocion != null) {
                respuesta = new PromocionDTO(
                        mejorPromocion.getPromocionId(),
                        mejorPromocion.getNombre(),
                        mejorPromocion.getPorcentajeDescuento(),
                        mejorPromocion.getActiva(),
                        true
                );
                System.out.println("Promoción encontrada: " + mejorPromocion.getNombre() +
                        " - " + mejorPromocion.getPorcentajeDescuento() + "%");
            } else {
                respuesta = new PromocionDTO(null, null, 0.0, false, false);
                System.out.println("No se encontró promoción aplicable");
            }

            // Enviar respuesta
            String respuestaJson = objectMapper.writeValueAsString(respuesta);
            Message<String> response = MessageBuilder
                    .withPayload(respuestaJson)
                    .setHeader(KafkaHeaders.TOPIC, "respuesta-consulta-promocion")
                    .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                    .build();

            kafkaTemplate.send(response);
            System.out.println("Respuesta enviada - CorrelationId: " + correlationId);

        } catch (Exception e) {
            System.err.println("Error procesando consulta de promoción: " + e.getMessage());
            e.printStackTrace();

            // Enviar respuesta de error
            try {
                PromocionDTO respuestaError = new PromocionDTO(null, null, 0.0, false, false);
                String respuestaJson = objectMapper.writeValueAsString(respuestaError);
                Message<String> response = MessageBuilder
                        .withPayload(respuestaJson)
                        .setHeader(KafkaHeaders.TOPIC, "respuesta-consulta-promocion")
                        .setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
                        .build();
                kafkaTemplate.send(response);
            } catch (Exception ex) {
                System.err.println("Error enviando respuesta de error: " + ex.getMessage());
            }
        }
    }
}
