package dev.yalerikk.paymentservice.domain;

import dev.yalerikk.paymentservice.api.dto.PaymentDto;
import dev.yalerikk.paymentservice.domain.db.PaymentEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMapper {
    public PaymentDto toDomain(PaymentEntity entity) {
        return new PaymentDto(
                entity.getId(),
                entity.getUserId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<PaymentDto> mapListToDto(List<PaymentEntity> payments) {
        return payments.stream()
                .map(this::toDomain)
                .toList();
    }
}
