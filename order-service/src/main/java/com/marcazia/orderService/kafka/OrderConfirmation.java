package com.marcazia.orderService.kafka;

import com.marcazia.orderService.customer.CustomerResponse;
import com.marcazia.orderService.order.PaymentMethod;
import com.marcazia.orderService.product.PurchaseResponse;

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
