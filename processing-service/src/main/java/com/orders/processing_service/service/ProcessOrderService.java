package com.orders.processing_service.service;


import com.orders.processing_service.payload.inbound.DeliveryOrder;

public interface ProcessOrderService {
    void processOrder(DeliveryOrder deliveryOrder);
}
