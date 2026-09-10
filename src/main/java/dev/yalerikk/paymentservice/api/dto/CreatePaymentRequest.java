package dev.yalerikk.paymentservice.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest (
        @NotNull(message = "userId must not be null")
        Long userId,

        @NotNull(message = "amount must not be null")
        @Positive(message = "amount must be positive")
        BigDecimal amount
){
}
