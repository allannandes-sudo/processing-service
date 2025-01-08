package com.orders.processing_service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatus {
    PENDING("Pending"),
    PROCESSED("Processed"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private String displayName;
}
