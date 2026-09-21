package com.api.payment_service.application.port.in;

import com.api.payment_service.application.dto.GetPaymentCommand;
import com.api.payment_service.application.dto.PaymentResponse;

public interface GetPaymentUseCase {
    PaymentResponse execute(GetPaymentCommand command);
}
