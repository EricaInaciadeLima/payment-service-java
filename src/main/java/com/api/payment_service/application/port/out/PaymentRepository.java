package com.api.payment_service.application.port.out;

import com.api.payment_service.domain.payment.Payment;

import java.util.Optional;
import java.util.UUID;

// Port Out:
// contrato que a Application define
// para persistir um Payment.
//
// Recebe:
//   Payment -> objeto do domínio
//
// Retorna:
//   Payment -> objeto do domínio persistido/resultado
public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(UUID paymentId);
}
