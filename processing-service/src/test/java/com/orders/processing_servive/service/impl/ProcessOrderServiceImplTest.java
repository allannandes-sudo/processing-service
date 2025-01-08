package com.orders.processing_servive.service.impl;

import com.orders.processing_servive.mapper.ProcessingOrderMapper;
import com.orders.processing_servive.model.Product;
import com.orders.processing_servive.payload.inbound.DeliveryOrder;
import com.orders.processing_servive.payload.outbound.ProcessingOrder;
import com.orders.processing_servive.service.CompletedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessOrderServiceImplTest {

    @Mock
    private ProcessingOrderMapper processingOrderMapper;

    @Mock
    private CompletedEvent completedEvent;

    @InjectMocks
    private ProcessOrderServiceImpl processOrderServiceImpl;


    @Test
    void shouldSendCompletedEventWithCorrectProcessingOrder() {
        // Arrange
        Product product1 = new Product();
        product1.setPrice(12.0);
        product1.setQuantity(1);

        Product product2 = new Product();
        product2.setPrice(8.0);
        product2.setQuantity(2);

        List<Product> products = Arrays.asList(product1, product2);
        DeliveryOrder deliveryOrder = new DeliveryOrder("orderId", "customerId", null, null, products, null);

        ProcessingOrder expectedProcessingOrder = new ProcessingOrder("orderId", "customerId", null, null, null, products, null, 28.0, 3);
        when(processingOrderMapper.toProcessingOrder(eq(deliveryOrder), eq(28.0), eq(3)))
                .thenReturn(expectedProcessingOrder);

        // Act
        processOrderServiceImpl.processOrder(deliveryOrder);

        // Assert
        verify(processingOrderMapper).toProcessingOrder(eq(deliveryOrder), eq(28.0), eq(3));
        verify(completedEvent).sendCompletedEvent(expectedProcessingOrder);
    }

    @Test
    void shouldMapDeliveryOrderToProcessingOrderWithCorrectTotalAndTotalQuantity() {
        // Arrange
        Product product1 = new Product();
        product1.setPrice(12.5);
        product1.setQuantity(4); // Total: 50.0

        Product product2 = new Product();
        product2.setPrice(7.5);
        product2.setQuantity(6); // Total: 45.0

        List<Product> products = Arrays.asList(product1, product2);
        DeliveryOrder deliveryOrder = new DeliveryOrder("orderId", "customerId", null, null, products, null);

        ProcessingOrder expectedProcessingOrder = new ProcessingOrder("orderId", "customerId", null, null, null, products, null, 95.0, 10);
        when(processingOrderMapper.toProcessingOrder(eq(deliveryOrder), eq(95.0), eq(10)))
                .thenReturn(expectedProcessingOrder);

        // Act
        processOrderServiceImpl.processOrder(deliveryOrder);

        // Assert
        verify(processingOrderMapper).toProcessingOrder(eq(deliveryOrder), eq(95.0), eq(10));
        verify(completedEvent).sendCompletedEvent(expectedProcessingOrder);
    }
}