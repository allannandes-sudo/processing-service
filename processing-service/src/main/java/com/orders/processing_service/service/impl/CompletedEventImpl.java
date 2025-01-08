package com.orders.processing_servive.service.impl;


import com.orders.processing_servive.kafka.producer.KafkaEventCompleted;
import com.orders.processing_servive.payload.outbound.ProcessingOrder;
import com.orders.processing_servive.service.CompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompletedEventImpl implements CompletedEvent {

    @Value("${topic.completed-event:null}")
    private String topic;
    private final KafkaEventCompleted kafkaEventCompleted;

    @Override
    public void sendCompletedEvent(ProcessingOrder processingOrder) {
        if (topic == null || topic.equals("null") || topic.isEmpty()) {
            log.warn("Topic is not configured properly");
            return;
        }
        log.info("Event sent to Kafka topic '{}' for orderId '{}'", topic, processingOrder.getOrderId());
        kafkaEventCompleted.sendStatus(topic, processingOrder);
    }
}
