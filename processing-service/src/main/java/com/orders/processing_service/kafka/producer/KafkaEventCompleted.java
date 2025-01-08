package com.orders.processing_service.kafka.producer;


import com.orders.processing_service.payload.outbound.ProcessingOrder;

public interface KafkaEventCompleted {
    void sendStatus(String kafkaTopic, ProcessingOrder payload);
}
