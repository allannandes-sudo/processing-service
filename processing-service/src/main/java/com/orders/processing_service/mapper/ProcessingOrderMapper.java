package com.orders.processing_servive.mapper;

import com.orders.processing_servive.model.enums.OrderStatus;
import com.orders.processing_servive.payload.inbound.DeliveryOrder;
import com.orders.processing_servive.payload.outbound.ProcessingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.orders.processing_servive.model.enums.OrderStatus.*;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ProcessingOrderMapper {

    @Mapping(target = "status", expression = "java(completedStatus(deliveryOrder))")
    @Mapping(target = "completedDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "priceTotal", source = "priceTotal")
    @Mapping(target = "quantityTotal", source = "quantityTotal")
    ProcessingOrder toProcessingOrder(DeliveryOrder deliveryOrder, Double priceTotal, Integer quantityTotal);

    default OrderStatus completedStatus(DeliveryOrder deliveryOrder) {
        return deliveryOrder.getStatus().equals(PROCESSED) ? COMPLETED : CANCELLED;
    }
}
