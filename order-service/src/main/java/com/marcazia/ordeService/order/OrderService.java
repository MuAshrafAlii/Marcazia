package com.marcazia.ordeService.order;

import com.marcazia.ordeService.BusinessException;
import com.marcazia.ordeService.customer.CustomerClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() ->
                        new BusinessException(
                                "Cannot create order. No customer exists with the provided ID: "
                                        + request.customerId()
                        )
                );
    }
}
