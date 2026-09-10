package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDto toDomain(PaymentEntity entity) {
        return new PaymentDto(
                entity.getId(),
                entity.getUserId(),
                entity.getAmount(),
                entity.getStatus().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
