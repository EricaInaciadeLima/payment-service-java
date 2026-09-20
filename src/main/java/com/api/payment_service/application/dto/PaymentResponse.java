package com.api.payment_service.application.dto;

import com.api.payment_service.domain.payment.PaymentStatus;

import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        UUID orderId,
        PaymentStatus status,
        String statusUrl
) {
}
