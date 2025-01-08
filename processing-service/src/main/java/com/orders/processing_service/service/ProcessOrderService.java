package com.orders.processing_servive.service;

import com.orders.processing_servive.payload.inbound.DeliveryOrder;

public interface ProcessOrderService {
    void processOrder(DeliveryOrder deliveryOrder);
}
