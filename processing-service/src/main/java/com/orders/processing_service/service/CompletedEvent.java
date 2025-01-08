package com.orders.processing_servive.service;

import com.orders.processing_servive.payload.outbound.ProcessingOrder;

public interface CompletedEvent {
    void sendCompletedEvent(ProcessingOrder processingOrder);
}
