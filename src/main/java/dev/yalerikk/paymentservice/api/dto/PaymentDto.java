package dev.yalerikk.paymentservice.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto (
    Long id,
    Long userId,
    BigDecimal amount,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
