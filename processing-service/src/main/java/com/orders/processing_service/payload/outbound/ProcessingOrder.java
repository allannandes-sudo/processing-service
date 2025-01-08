package com.orders.processing_servive.payload.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orders.processing_servive.annotations.ZonedDataTimeFormatter;
import com.orders.processing_servive.model.Product;
import com.orders.processing_servive.model.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 6136932342667013799L;

    @NotNull(message = "O campo orderId é obrigatório.")
    private String orderId;
    @NotNull(message = "O campo customerId é obrigatório.")
    private String customerId;
    @ZonedDataTimeFormatter
    private LocalDateTime createDateTime;
    @ZonedDataTimeFormatter
    private LocalDateTime sendDateTime;
    @ZonedDataTimeFormatter
    private LocalDateTime completedDateTime;
    @NotEmpty(message = "A lista de produtos não pode estar vazia.")
    private List<Product> products;
    private OrderStatus status;
    private Double priceTotal;
    private Integer quantityTotal;
}
