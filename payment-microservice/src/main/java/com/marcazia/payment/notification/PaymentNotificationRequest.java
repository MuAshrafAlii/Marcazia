package com.marcazia.payment.notification;

import com.marcazia.payment.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String firstName,
        String lastName,
        String email
) {
}
