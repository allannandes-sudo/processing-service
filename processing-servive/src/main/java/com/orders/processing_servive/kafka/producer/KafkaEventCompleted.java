package com.orders.processing_servive.kafka.producer;


import com.orders.processing_servive.payload.outbound.ProcessingOrder;

public interface KafkaEventCompleted {
    void sendStatus(String kafkaTopic, ProcessingOrder payload);
}
