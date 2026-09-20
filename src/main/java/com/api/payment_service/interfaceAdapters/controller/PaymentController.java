package com.api.payment_service.interfaceAdapters.controller;

import com.api.payment_service.application.dto.CreatePaymentCommand;
import com.api.payment_service.application.dto.PaymentResponse;
import com.api.payment_service.application.port.in.CreatePaymentUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentCommand command) {
        return ResponseEntity.ok(createPaymentUseCase.execute(command));
    }


}