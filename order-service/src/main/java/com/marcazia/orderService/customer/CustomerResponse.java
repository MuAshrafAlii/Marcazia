package com.marcazia.orderService.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
