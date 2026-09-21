package com.api.payment_service.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetPaymentCommand (
        @NotNull
        UUID paymentId
){}
