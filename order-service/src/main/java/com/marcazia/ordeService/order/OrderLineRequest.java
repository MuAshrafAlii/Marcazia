package com.marcazia.ordeService.order;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
