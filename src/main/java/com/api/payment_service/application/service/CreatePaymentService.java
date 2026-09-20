package com.api.payment_service.application.service;

import com.api.payment_service.application.dto.CreatePaymentCommand;
import com.api.payment_service.application.dto.PaymentResponse;
import com.api.payment_service.application.port.in.CreatePaymentUseCase;
import com.api.payment_service.application.port.out.PaymentRepository;
import com.api.payment_service.domain.payment.Payment;
import com.api.payment_service.domain.payment.PaymentStatus;

import java.util.UUID;

public class CreatePaymentService implements CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;

    public CreatePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse execute(CreatePaymentCommand command){
        Payment payment = new Payment(
                UUID.randomUUID(),//gerar o id automático
                command.orderId(),
                command.amount(),
                command.currency(),
                command.paymentMethod(),
                PaymentStatus.PENDING//Para criar pagamento, a regra de dominio determina seu estado inicial PENDING
        );

        Payment savedPayment = paymentRepository.save(payment);

//        onde montar a url de pagamento?
//        String statusUrl = "/payments/" + savedPayment.getId();

        return new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getOrderId(),
                savedPayment.getStatus(),
                null
        );
    }
}
