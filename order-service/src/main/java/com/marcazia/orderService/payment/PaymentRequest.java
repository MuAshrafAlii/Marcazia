package com.marcazia.orderService.payment;

import com.marcazia.orderService.customer.CustomerResponse;
import com.marcazia.orderService.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
