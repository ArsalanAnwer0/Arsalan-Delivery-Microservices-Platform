package dev.vundirov.orderservice.api.dto.get;

import dev.vundirov.orderservice.domain.PaymentStatus;
import dev.vundirov.orderservice.domain.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link OrderEntity}
 */
public record RequestGetOrderDto(Long id, PaymentStatus paymentStatus,
                                 BigDecimal total_cost,
                                 Set<RequestGetItemDto> items) {
}