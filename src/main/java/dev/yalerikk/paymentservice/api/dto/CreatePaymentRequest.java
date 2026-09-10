package dev.yalerikk.paymentservice.api.dto;

import java.math.BigDecimal;

// TODO: add validation nonnull + positive
public record CreatePaymentRequest (
        Long userId,
        BigDecimal amount
){
}
