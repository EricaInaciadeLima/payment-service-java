package com.api.payment_service.domain.payment;
//Estado do pagamento
public enum PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED,
    REFUNDED
}
