package com.api.payment_service.infrastructure.persistence.repository;

import com.api.payment_service.application.port.out.PaymentRepository;
import com.api.payment_service.domain.payment.Payment;
import com.api.payment_service.infrastructure.persistence.entity.PaymentEntity;
import com.api.payment_service.infrastructure.persistence.mapper.PaymentPersistenceMapper;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Payment> findById(UUID paymentId) {
        Optional<PaymentEntity> entity = jpaPaymentRepository.findById(paymentId);

        // verifica se o pagamento não foi encontrado
        if(entity.isEmpty()){
            // retorna um Optional sem PaymentEntity
            return Optional.empty();
        }

        // obtém a PaymentEntity encontrada
        // e transforma em objeto de domínio Payment
        Payment payment = paymentPersistenceMapper.toDomain(entity.get());

        // cria um Optional contendo o Payment encontrado
        return Optional.of(payment);
    }
}
