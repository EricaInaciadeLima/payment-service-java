package com.api.payment_service.interfaceAdapters.controller;

import com.api.payment_service.application.dto.CreatePaymentCommand;
import com.api.payment_service.application.dto.GetPaymentCommand;
import com.api.payment_service.application.dto.PaymentResponse;
import com.api.payment_service.application.port.in.CreatePaymentUseCase;
import com.api.payment_service.application.port.in.GetPaymentUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase, GetPaymentUseCase getPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.getPaymentUseCase = getPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentCommand command) {
        return ResponseEntity.ok(createPaymentUseCase.execute(command));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentId(@Valid @PathVariable GetPaymentCommand command) {
        return ResponseEntity.ok(getPaymentUseCase.execute(command));
    }
}