package com.orders.processing_servive.service.impl;


import com.orders.processing_service.kafka.producer.KafkaEventCompleted;
import com.orders.processing_service.model.Product;
import com.orders.processing_service.model.enums.OrderStatus;
import com.orders.processing_service.payload.outbound.ProcessingOrder;
import com.orders.processing_service.service.impl.CompletedEventImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompletedEventImplTest {
    @Mock
    private KafkaEventCompleted kafkaEventCompleted;

    @InjectMocks
    private CompletedEventImpl completedEventImpl;

    @Test
    void shouldCallKafkaEventCompletedSendStatusWithCorrectTopicAndProcessingOrder() {
        String expectedTopic = "completed-event-topic";
        ProcessingOrder processingOrder = new ProcessingOrder("12345", "customerId",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                List.of(new Product()), OrderStatus.PROCESSED, 100.0, 10);


        ReflectionTestUtils.setField(completedEventImpl, "topic", expectedTopic);


        completedEventImpl.sendCompletedEvent(processingOrder);


        verify(kafkaEventCompleted, times(1)).sendStatus(expectedTopic, processingOrder);
    }

    @Test
    void shouldSendEventToKafkaWhenTopicIsCorrectlyConfigured() {
        String validTopic = "valid-topic";
        ProcessingOrder processingOrder = new ProcessingOrder("12345", "customerId",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                List.of(new Product()), OrderStatus.PROCESSED, 100.0, 10);


        ReflectionTestUtils.setField(completedEventImpl, "topic", validTopic);


        completedEventImpl.sendCompletedEvent(processingOrder);


        verify(kafkaEventCompleted, times(1)).sendStatus(validTopic, processingOrder);
    }
}