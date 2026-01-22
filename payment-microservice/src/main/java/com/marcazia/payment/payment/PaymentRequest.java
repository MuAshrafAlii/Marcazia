package com.marcazia.payment.payment;

import jakarta.validation.Valid;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        @Valid Customer customer
) {
}
