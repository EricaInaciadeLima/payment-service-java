package com.api.payment_service.application.service;

import com.api.payment_service.application.dto.GetPaymentCommand;
import com.api.payment_service.application.dto.PaymentResponse;
import com.api.payment_service.application.exception.PaymentNotFoundException;
import com.api.payment_service.application.port.in.GetPaymentUseCase;
import com.api.payment_service.application.port.out.PaymentRepository;
import com.api.payment_service.domain.payment.Payment;

public class GetPaymentService implements GetPaymentUseCase {
    private final PaymentRepository paymentRepository;

    public GetPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse execute(GetPaymentCommand command) {
        Payment getPayment = paymentRepository.findById(command.paymentId())
                .orElseThrow(()-> new PaymentNotFoundException("Payment not found!"));

        PaymentResponse result = new PaymentResponse(
                getPayment.getId(),
                getPayment.getOrderId(),
                getPayment.getStatus(),
                null
        );

        return result;
    }
}
