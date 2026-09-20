package com.api.payment_service.infrastructure.persistence.repository;

import com.api.payment_service.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
//spring Data sabe como persistir um paymententity ou qualquer outra operação relacionado ao banco, como: select, insert, delete, update, etc
public interface JpaPaymentRepository  extends JpaRepository<PaymentEntity, UUID> {
}
