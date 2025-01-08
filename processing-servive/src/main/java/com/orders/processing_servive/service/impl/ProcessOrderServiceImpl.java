package com.orders.processing_servive.service.impl;


import com.orders.processing_servive.mapper.ProcessingOrderMapper;
import com.orders.processing_servive.model.Product;
import com.orders.processing_servive.payload.inbound.DeliveryOrder;
import com.orders.processing_servive.payload.outbound.ProcessingOrder;
import com.orders.processing_servive.service.CompletedEvent;
import com.orders.processing_servive.service.ProcessOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessOrderServiceImpl implements ProcessOrderService {

    private final ProcessingOrderMapper processingOrderMapper;
    private final CompletedEvent completedEvent;

    @Override
    public void processOrder(DeliveryOrder deliveryOrder) {
        log.info("Processando pedido: {}", deliveryOrder);
        Double total = deliveryOrder.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();

        Integer totalQuantity = deliveryOrder.getProducts().stream()
                .mapToInt(Product::getQuantity)
                .sum();
        ProcessingOrder processingOrder = processingOrderMapper.toProcessingOrder(deliveryOrder, total, totalQuantity);
        log.info("Pedido processado: {}", processingOrder);
        completedEvent.sendCompletedEvent(processingOrder);
    }
}
