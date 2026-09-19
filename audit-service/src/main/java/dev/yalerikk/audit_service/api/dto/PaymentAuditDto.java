package dev.yalerikk.audit_service.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentAuditDto(
        Long id,
        UUID eventId,
        Long paymentId,
        String eventType,
        String status,
        BigDecimal amount,
        LocalDateTime occurredAt,
        LocalDateTime receivedAt
) {
}
