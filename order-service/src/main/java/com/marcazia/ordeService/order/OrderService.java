package com.marcazia.ordeService.order;

import com.marcazia.ordeService.exception.BusinessException;
import com.marcazia.ordeService.customer.CustomerClient;
import com.marcazia.ordeService.product.ProductClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

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
