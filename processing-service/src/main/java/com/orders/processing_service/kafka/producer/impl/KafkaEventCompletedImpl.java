package com.orders.processing_service.kafka.producer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.processing_service.kafka.producer.KafkaEventCompleted;
import com.orders.processing_service.kafka.producer.KafkaEventProducer;
import com.orders.processing_service.payload.outbound.ProcessingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaEventCompletedImpl extends KafkaEventProducer<ProcessingOrder> implements KafkaEventCompleted {

    public KafkaEventCompletedImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        super(objectMapper, kafkaTemplate, "order-service");
    }

    @Override
    public void sendStatus(String kafkaTopic, ProcessingOrder payload) {
        log.info("Sending kafka topic KafkaEventCompletedImpl: {}, ProcessingOrder: {}", kafkaTopic, payload);
        sendKafka(kafkaTopic, payload);
    }
}