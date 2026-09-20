package com.api.payment_service.application.dto;

import com.api.payment_service.domain.payment.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand(
        @NotNull
        UUID orderId,
        @NotNull
        BigDecimal amount,
        @NotBlank
        String currency,
        @NotNull
        PaymentMethod paymentMethod
) {
}
