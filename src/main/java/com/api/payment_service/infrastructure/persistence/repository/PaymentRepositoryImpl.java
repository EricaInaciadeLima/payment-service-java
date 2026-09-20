package com.api.payment_service.infrastructure.persistence.repository;

import com.api.payment_service.application.port.out.PaymentRepository;
import com.api.payment_service.domain.payment.Payment;
import com.api.payment_service.infrastructure.persistence.entity.PaymentEntity;
import com.api.payment_service.infrastructure.persistence.mapper.PaymentPersistenceMapper;

public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    public PaymentRepositoryImpl(JpaPaymentRepository jpaPaymentRepository, PaymentPersistenceMapper paymentPersistenceMapper) {
        this.jpaPaymentRepository = jpaPaymentRepository;
        this.paymentPersistenceMapper = paymentPersistenceMapper;
    }

    @Override
    public Payment save(Payment payment) {
        //dominio -> entidade de persistencia
        PaymentEntity entity = paymentPersistenceMapper.toEntity(payment);
        // entidade -> postgressql através do jpa
        PaymentEntity savedEntity = jpaPaymentRepository.save(entity);
        //entidade persistida -> dominio
          return paymentPersistenceMapper.toDomain(savedEntity);
    }
}
