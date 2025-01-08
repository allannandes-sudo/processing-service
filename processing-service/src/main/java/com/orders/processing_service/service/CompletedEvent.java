package com.orders.processing_service.service;


import com.orders.processing_service.payload.outbound.ProcessingOrder;

public interface CompletedEvent {
    void sendCompletedEvent(ProcessingOrder processingOrder);
}
