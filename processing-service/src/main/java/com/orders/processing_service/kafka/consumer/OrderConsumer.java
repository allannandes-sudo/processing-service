package com.orders.processing_servive.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.processing_servive.exception.MessageProcessingException;
import com.orders.processing_servive.payload.inbound.DeliveryOrder;
import com.orders.processing_servive.service.ProcessOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final ProcessOrderService processOrderService;


    @KafkaListener(
            topics = "${topic.process-event:null}",
            groupId = "${spring.kafka.consumer.group-id}",
            clientIdPrefix = "order-processor"
    )
    @Transactional
    public void updateOrderProcessed(@Payload String message) {
        log.info("Pedido recebido: {}", message);
        try {
            var dto = objectMapper.readValue(message, DeliveryOrder.class);
            processOrderService.processOrder(dto);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem JSON: {}", message, e);
            throw new MessageProcessingException("Erro ao processar mensagem JSON", e);
        } catch (Exception e) {
            log.error("Erro ao processar pedido: {}", e.getMessage(), e);
            throw new MessageProcessingException("Erro ao processar pedido", e);
        }
    }

}
