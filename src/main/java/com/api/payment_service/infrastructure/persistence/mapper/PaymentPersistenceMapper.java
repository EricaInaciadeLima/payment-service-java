package com.api.payment_service.infrastructure.persistence.mapper;

import com.api.payment_service.domain.payment.Payment;
import com.api.payment_service.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
//                 MAPPER
//
//Payment ────────────────→ PaymentEntity
// domínio                   banco
//
//Payment ←──────────────── PaymentEntity
@Component
public class PaymentPersistenceMapper {

    public PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getMethod(),
                payment.getStatus()
        );
        entity.setCreatedAt(Instant.now());

        return entity;
    }

    public Payment toDomain(PaymentEntity entity) {
        Payment payment = new Payment(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getPaymentMethod(),
                entity.getStatus()
        );


        return payment;
    }
}
