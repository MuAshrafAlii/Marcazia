package com.marcazia.ordeService.kafka;

import com.marcazia.ordeService.customer.CustomerResponse;
import com.marcazia.ordeService.order.PaymentMethod;
import com.marcazia.ordeService.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
